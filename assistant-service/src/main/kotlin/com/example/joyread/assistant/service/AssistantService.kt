package com.example.joyread.assistant.service

import com.example.joyread.assistant.domain.dto.ChatCompletionRequestDTO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerTextVO
import com.example.joyread.assistant.domain.vo.AssistantAnswerSourceVO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerVO
import com.example.joyread.assistant.handler.AIHandler
import com.example.joyread.common.domain.vo.NewsTitleVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.springframework.stereotype.Service

@Service
class AssistantService(
    private val aiHandler: AIHandler
) {
    suspend fun chat(userId: String, question: String): Flow<ChunkAssistantAnswerVO> {
        val chatCompletionRequestDTO = ChatCompletionRequestDTO.streaming(userId, question)
        return aiHandler.sendChatMessages(chatCompletionRequestDTO).transform { json ->
            val eventType = json.get("event").asText()
            if (eventType == "node_finished") {
                /* While directly asking *latest news*, *today's popular news* or *this week's popular news*, no
                   `retriever_resources` will appear in `message_end` event. Instead, extract citations and attributions
                   only in `code` node. */
                val data = json.get("data")
                val nodeId = data.get("node_id").asText()
                if (nodeId == "1747272934532") {
                    val outputs = data.get("outputs")
                    val results = outputs.get("result").asIterable()

                    val newsTitleVOs = results.map { result ->
                        val id = result.get("id").asText()
                        val title = result.get("title").asText()
                        NewsTitleVO(id, title)
                    }
                    val answerSourceVO = AssistantAnswerSourceVO(newsTitleVOs)
                    emit(answerSourceVO)
                }

            } else if (eventType == "message") {
                val answer = json.get("answer").asText()

                val chunkAnswerTextVO = ChunkAssistantAnswerTextVO(answer)
                emit(chunkAnswerTextVO)

            } else if (eventType == "message_end") {
                val metadata = json.get("metadata")
                val retrieverResources = metadata.get("retriever_resources").asIterable()

                val newsTitleVOs = retrieverResources.map { retrieverResource ->
                    val documentName = retrieverResource.get("document_name").asText()

                    val docMetadata = retrieverResource.get("doc_metadata")
                    val id = docMetadata.get("id").asText()

                    NewsTitleVO(id, documentName)
                }
                val answerSourceVO = AssistantAnswerSourceVO(newsTitleVOs)
                emit(answerSourceVO)
            }
        }
    }

    suspend fun chatUnderContext(userId: String, question: String, newsId: String): Flow<ChunkAssistantAnswerTextVO> {
        val chatCompletionRequestDTO = ChatCompletionRequestDTO.streaming(userId, question, newsId)
        return aiHandler.sendChatMessages(chatCompletionRequestDTO).transform { json ->
            val eventType = json.get("event").asText()
            if (eventType == "message") {
                val answer = json.get("answer").asText()

                val chunkAnswerTextVO = ChunkAssistantAnswerTextVO(answer)
                emit(chunkAnswerTextVO)
            }
        }
    }
}
