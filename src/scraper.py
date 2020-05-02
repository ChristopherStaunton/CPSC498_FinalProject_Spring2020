#!/usr/bin/python
from bs4 import BeautifulSoup
from lxml import html
import requests
import sys

results = []
searchD = True
outFile = "output.txt"
inFile = "input.txt"

# Puts found words into output file
def putResultsInFile():
    global outFile
    output_File = open(outFile, "w")
    for w in results:
        output_File.write(w)
    output_File.close()
        
# Cleans results
def organizeMethodDOutput():
    print("|organizing results...")
    global outFile
    output_File = open(outFile, "r")
    toBeCleaned = output_File.read()
    output_File.close()
    uncleanList = list(toBeCleaned)
    output_File = open(outFile, "w")
    preWordCounter = 0
    lineCounter = 0
    pre = list('"word":"')
    cur = ''
    for c in uncleanList:
        if preWordCounter >= 8 and uncleanList[lineCounter] != '"':
            cur = cur + uncleanList[lineCounter]
        elif preWordCounter >= 8 and uncleanList[lineCounter] == '"':
            results.append(cur)
            output_File.write(cur + '\n')
            cur = ''
            preWordCounter = 0
        elif pre[preWordCounter] == uncleanList[lineCounter]:
            preWordCounter = preWordCounter + 1
        lineCounter = lineCounter + 1
    output_File.close()
    printResults()

# Gathers words based on topic from relatedwords.org
def searchMethodD(topic):
    print("|starting search method D...")
    global outFile
    page = requests.get('https://relatedwords.org/api/related?term=' + topic)
    soup = BeautifulSoup(page.content, 'html.parser')
    output_File = open(outFile, "w")
    output_File.write(str(soup))
    output_File.close()
    organizeMethodDOutput()

# Prints current results
def printResults():
    print("|printing results")
    for a in results:
        print(a)

# Selects search method
def search(sTopic, sType, sInput):
    print("|choosing search method...")
    global searchD
    resultsFound = False
    if sType == 'd' and searchD:
        searchMethodD(sTopic)
        resultsFound = True
        searchD = False
    elif searchD:
        searchMethodD(sTopic)
        resultsFound = True
        searchD = False
    return True;

# Prepares for search
def programStart(searchTopic, searchType, searchInput):
    print("|preparing...")
    searchesDone = False
    while not searchesDone:
        searchesDone = search(searchTopic, searchType, searchInput)

# Gathers input for search
print("|started")
input_file = open(inFile, "r")
aSearchTerm = input_file.read()
print("|searching for words like <" + aSearchTerm + ">...")
input_file.close()
programStart(aSearchTerm, 'd', 0)
print("|finished")
