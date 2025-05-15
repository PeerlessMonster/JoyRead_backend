def main(http_response: str) -> dict:
    import json
    data_list = json.loads(http_response)
    news_title_list = [{'id': data['id'], 'title': data['title']} for data in data_list]
    return {
        'result': news_title_list
    }
