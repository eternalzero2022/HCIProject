from crawler import get_collection_by_id
from model import Category
from backend import Backend

def main():
    backend = Backend()
    request_id = input("请输入虎扑评分 ID: ")
    print("\n请选择所属分类:")
    for index, category in enumerate(Category, start=1):
        print(f"{index}. {category.value}")
    while True:
        try:
            category_choice = int(input("请输入类别数字（1-6）: "))
            print(category_choice)
            if 1 <= category_choice <= 6:
                selected_category = list(Category)[category_choice - 1]
                break
            else:
                print("无效选择，请输入 1 到 6 之间的数字。")
        except (ValueError, IndexError):
            print("无效输入，请输入数字。")

    collection = get_collection_by_id(request_id)
    collection.category = selected_category
    print(collection.output())
    
    confirm = input("您是否确认？ (y/n): ").strip().lower()
    if confirm == 'y':
        backend.create_collection(collection)
        print("已完成！")
    else:
        print("已取消。")

if __name__ == "__main__":
    while(True):
        main()
        if input("是否继续？(y/n): ") == 'n':
            break