from model import User, Item, Collection
import requests
from crawler import get_collection_by_id
import re
from util import clean_dict

SERVER = '127.0.0.1:8080'
REGISTER = 'api/users/register'
LOGIN = 'api/users/login'
GET_USER = 'api/users'
CREATE_COLLECTION = 'api/collections'
UPLOAD = 'api/images'

PHONE = '1223341434'
PASSWORD = '123456'
USERNAME = '搬砖工'
IMAGE_URL = 'https://sprooc-pic.oss-cn-hangzhou.aliyuncs.com/202410311812846.jpg'

class Backend:
    def __init__(self):
        self.cookie = ''
        user = User(USERNAME, PHONE, PASSWORD, IMAGE_URL)
        self.register(user)
        self.login(PHONE, PASSWORD)

    def register(self, user: User):
        url = f'http://{SERVER}/{REGISTER}'
        data = user.to_json()
        response = requests.post(url, json=data)

    def login(self, phone: str, password: str):
        url = f'http://{SERVER}/{LOGIN}'
        data = {
            'phone': phone,
            'password': password
        }
        response = requests.post(url, json=data)
        self.cookie = 'JSESSIONID=' + response.cookies.get_dict()['JSESSIONID']

    def get_user(self):
        url = f'http://{SERVER}/{GET_USER}'
        headers = {
            'Cookie': self.cookie
        }
        response = requests.get(url, headers=headers)
    
    def create_collection(self, collection: Collection):
        url = f'http://{SERVER}/{CREATE_COLLECTION}'
        headers = {
            'Cookie': self.cookie,
        }
        data = collection.to_json()
        data = clean_dict(data)
        # print(data)
        response = requests.post(url, headers=headers, json=data)
        print(response.text)

    def upload(self, file_path):
        file = {'file': open(file_path, 'rb')}
        url = f'http://{SERVER}/{UPLOAD}'
        headers = {
            'Cookie': self.cookie,
        }
        response = requests.post(url, headers=headers, files=file)
        json_data = response.json()
        return json_data['result']