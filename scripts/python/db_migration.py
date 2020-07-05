import csv

from pymongo import MongoClient

client = MongoClient("mongodb://localhost:27017")
db = client.dogger

with open('exported/public_place.csv') as csvfile:
    place_reader = csv.DictReader(csvfile)
    for row in place_reader:
        db.place.insert_one(row)

with open('exported/public_place_amenity.csv') as csvfile:
    place_reader = csv.DictReader(csvfile)
    for row in place_reader:
        db.place.update_one({'old_id': row["place_id"]}, {'$push': {'amenities': row["amenity"]}})

with open('exported/public_place_contact.csv') as csvfile:
    place_reader = csv.DictReader(csvfile)
    for row in place_reader:
        db.place.update_one({'old_id': row["place_id"]}, {'$push': {'contacts': { 'value': row["contact_value"], 'contactType': row["contact_type"]}}})

with open('exported/public_timecard.csv') as csvfile:
    place_reader = csv.DictReader(csvfile)
    for row in place_reader:
        db.place.update_one({'old_id': row["id"]}, {'$set' :{'timecard' : row}})
