import re

def clean_string(input_string):
    # print(input_string, input_string.encode('utf-8', 'ignore').decode('utf-8'))

    # return input_string.encode('utf-8', 'ignore').decode('utf-8')
    return input_string[:255]

def clean_dict(data):
    for key, value in data.items():
        if isinstance(value, str):
            data[key] = clean_string(value)
        elif isinstance(value, dict):
            data[key] = clean_dict(value)
        elif isinstance(value, list):
            for i in range(len(value)):
                value[i] = clean_dict(value[i])
            data[key] = value
    return data