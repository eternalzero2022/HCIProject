from enum import Enum
from typing import List, Dict, Any
from dataclasses import dataclass, asdict

@dataclass
class Item:
    """Item"""
    collection_id: int
    description: str
    displayable: bool
    image_url: str
    item_id: int
    item_name: str
    vote_count: int
    win_rate: float

    def __init__(self):
        self.collection_id = 0
        self.description = ''
        self.displayable = True
        self.image_url = ''
        self.item_id = 0
        self.item_name = ''
        self.vote_count = 0
        self.win_rate = 0.0

    def to_json(self) -> Dict[str, Any]:
        return {
            "collectionId": self.collection_id,
            "description": self.description,
            "displayable": self.displayable,
            "imageUrl": self.image_url,
            "itemId": self.item_id,
            "itemName": self.item_name,
            "voteCount": self.vote_count,
            "winRate": self.win_rate
        }

    def output(self):
        return f'{self.item_name} - {self.description}'

@dataclass
class User:
    image_url: str
    password: str
    phone: str
    user_id: int
    username: str

    def __init__(self, username='', phone='', password='', image_url=''):
        self.image_url = image_url
        self.password = password
        self.phone = phone
        self.user_id = 0
        self.username = username


    def to_json(self) -> Dict[str, Any]:
        return {
            "imageUrl": self.image_url,
            "password": self.password,
            "phone": self.phone,
            "userId": self.user_id,
            "username": self.username
        }

class Category(Enum):
    ANIMATION = "ANIMATION"
    FOOD = "FOOD"
    GAME = "GAME"
    LIFE = "LIFE"
    MEDIA = "MEDIA"
    SPORTS = "SPORTS"

@dataclass
class Collection:
    category: Category
    collection_id: int
    collection_name: str
    creator_id: int
    description: str
    image_url: str
    items: List[Item]
    vote_count: int

    def __init__(self):
        self.category = Category.ANIMATION
        self.collection_id = 0
        self.collection_name = ''
        self.creator_id = 0
        self.description = ''
        self.image_url = ''
        self.items = []
        self.vote_count = 0

    def to_json(self) -> Dict[str, Any]:
        return {
            "category": self.category.value,  # Use Enum value for JSON
            "collectionId": self.collection_id,
            "collectionName": self.collection_name,
            "creatorId": self.creator_id,
            "description": self.description,
            "imageUrl": self.image_url,
            "items": [item.to_json() for item in self.items],  # Convert items to JSON
            "voteCount": self.vote_count
        }
    
    def output(self):
        ostr = f'{self.collection_name} - {self.description} - {self.category.value}'
        for item in self.items:
            ostr += '\n\t\t' + item.output()
        return ostr
