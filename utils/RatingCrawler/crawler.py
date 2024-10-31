import requests
import json
from model import Collection, Item


def get_collection_by_id(id):
    # url = "https://m.hupu.com/score-list/common_first/"+str(id)+"?page-size=100"
    url = f'https://games.mobileapi.hupu.com/1/8.0.99/bplcommentapi/bpl/score_tree/getCurAndSubNodeByBizKey?relation=CHILD&page=1&pageSize=1000&outBizNo={id}&outBizType=common_first'
    response = requests.get(url)
    json_rd = ''

    if response.status_code == 200:
        json_rd = response.text
    else:
        print("Failure code: ", response.status_code)
        exit(1)
    json_data = json.loads(json_rd)
    json_data = json_data['data']
    collection = Collection()
    collection.collection_name = json_data['self']['node']['name']
    collection.description = json_data['self']['node']['infoJson']['desc'][0] if 'desc' in json_data['self']['node']['infoJson'] else ''
    collection.image_url = json_data['self']['node']['image'][0]

    json_items = json_data['pageResult']['data']
    for json_item in json_items:
        json_item = json_item["node"]
        item = Item()
        item.image_url = json_item["image"][0]
        item.item_name = str(json_item["name"])
        item.description = json_item["hottestComments"][0] if len(json_item["hottestComments"])>0 else ''
        collection.items.append(item)

    return collection