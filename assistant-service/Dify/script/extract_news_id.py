def main(http_response: str) -> dict:
    import json
    data_list = json.loads(http_response)
    id_list = [data['id'] for data in data_list]
    return {
        "result": id_list,
    }
