import requests
from urllib.parse import urlparse
import os


def download_image(url, save_path=""):
    if not os.path.exists('cache'):
      os.makedirs('cache')
    if save_path == "":
      parsed_url = urlparse(url)
      save_path = 'cache/' + parsed_url.path.split("/")[-1]
    try:
        response = requests.get(url, stream=True)
        if response.status_code == 200:
            with open(save_path, 'wb') as file:
                for chunk in response.iter_content(1024):
                    file.write(chunk)
            print(f"图片已成功保存到 {save_path}")
            return save_path
        else:
            print(f"请求失败，状态码: {response.status_code}")
    except Exception as e:
        print(f"下载图片时出错: {e}")
    

