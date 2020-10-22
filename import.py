import requests
from pymongo import MongoClient
from Covid import Covid


client = MongoClient('localhost', 27017, username='admin', password='pass')
db = client['DataIntensive_DB']
collection_covid = db['covid']

#URL = "https://api.covidtracking.com/v1/states/current.json"
URL = "https://api.covidtracking.com/v1/states/daily.json"

r = requests.get(url=URL)

data = r.json()



for stateCases in data:
    covid = Covid(date=stateCases["date"], state=stateCases["state"], newCases=stateCases["positiveIncrease"], dataQualityGrade=stateCases["dataQualityGrade"])
    collection_covid.insert_one(covid.__dict__)

client.close()