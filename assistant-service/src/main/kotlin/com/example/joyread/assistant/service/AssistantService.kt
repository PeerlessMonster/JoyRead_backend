package com.example.joyread.assistant.service

import com.example.joyread.assistant.domain.dto.ChatCompletionRequestDTO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerTextVO
import com.example.joyread.assistant.domain.vo.AssistantAnswerSourceVO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerVO
import com.example.joyread.common.constant.HTTPMediaType
import com.example.joyread.common.domain.vo.NewsTitleVO
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow

@Service
class AssistantService(
    private val aiClient: WebClient
) {
    suspend fun chat(userId: String, question: String, newsId: String? = null): Flow<ChunkAssistantAnswerVO> {
        val chatCompletionRequestDTO = if (newsId == null) {
            ChatCompletionRequestDTO(userId, question)
        } else {
            ChatCompletionRequestDTO(userId, question, newsId)
        }

        var newsTitleVOs: List<NewsTitleVO>? = null

        return aiClient.post()
            .uri("/chat-messages")
            .contentType(HTTPMediaType.APPLICATION_JSON_UTF8)
            .bodyValue(chatCompletionRequestDTO)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlow<JsonNode>()
            .transform { json ->
                val eventType = json.get("event").asText()
                if (eventType == "node_finished") {
                    val data = json.get("data")
                    val nodeType = data.get("node_type").asText()
                    if (nodeType == "code" || nodeType == "knowledge-retrieval") {
                        val outputs = data.get("outputs")
                        val results = outputs.get("result").asIterable()

                        newsTitleVOs = results.map { result ->
                            val title = result.get("title").asText()

                            val id = if (nodeType == "code") {
                                result.get("id").asText()
                            } else {
                                val metadata = result.get("metadata")
                                val docMetadata = metadata.get("doc_metadata")
                                docMetadata.get("id").asText()
                            }

                            NewsTitleVO(id, title)
                        }
                    }
                } else if (eventType == "message") {
                    val answer = json.get("answer").asText()

                    val chunkAnswerTextVO = ChunkAssistantAnswerTextVO(answer)
                    emit(chunkAnswerTextVO)

                } else if (eventType == "workflow_finished") {
                    if (newsTitleVOs != null) {
                        val answerSourceVO = AssistantAnswerSourceVO(newsTitleVOs)
                        emit(answerSourceVO)
                    }
                }
            }
    }
}
