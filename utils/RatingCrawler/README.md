## Feature

从虎扑爬取评分榜的数据，包括名称、简介、图片。

## Denpendency

依赖的库只有`requests`。

## Usage

1. 运行后端服务器

2. 在虎扑上找到想要找到的评分榜单，点击右上角三点，然后复制链接。

   ![image-20241031220159283](https://sprooc-pic.oss-cn-hangzhou.aliyuncs.com/pics/image-20241031220159283.png)

3. 得到一个链接，比如下面这个。最后面的数字2048450就是我们需要的id。

   ```
   https://m.hupu.com/score/detail.html?outBizType=common_first&outBizNo=2048450
   ```

4. `python3 main.py`运行脚本。

5. 输入之前获取的id，手动选择类别（因为虎扑上面没有类别信息）

   ![image-20241031220812810](https://sprooc-pic.oss-cn-hangzhou.aliyuncs.com/pics/image-20241031220812810.png)

6. 回车之后会显示爬到的数据，如果没问题就输入y确认，数据就录入好了。

7. 可以重复录入。