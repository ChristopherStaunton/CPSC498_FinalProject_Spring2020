#!/usr/bin/python
from bs4 import BeautifulSoup
from lxml import html
import requests

import sys

# Global Variables
# list of results
results = []
# which search methods are available
searchD = True
outFile = "output.txt"
inFile = "input.txt"

def putResultsInFile():
    global outFile
    output_File = open(outFile, "w")
    for w in results:
        output_File.write(w)
    output_File.close()
        

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
#    putResultsInFile()
    printResults()


# Search a web site that finds related words
# Still being worked on
# Issues with flexbox
# stable / effective / numerous
# https://relatedwords.org/
def searchMethodD(topic):
    print("|starting search method D...")
    global outFile
    page = requests.get('https://relatedwords.org/api/related?term=' + topic)
    soup = BeautifulSoup(page.content, 'html.parser')
    output_File = open(outFile, "w")
    output_File.write(str(soup))
    output_File.close()
    organizeMethodDOutput()


# Determines if results are sufficient
# Currently only return true for testing purposes
# Incomplete
def areResultsGood():
    return True


# Prints current results
def printResults():
    print("|printing results")
    for a in results:
        print(a)


# Clean results array of extra unnecessary characters
# Incomplete
# Waiting until search methods are complete
def cleanResults():
    print()


# Selects next search method
def search(sTopic, sType, sInput):
    print("|choosing search method...")
    global searchD
    resultsFound = False
    if sType == 'd' and searchD:
        searchMethodD(sTopic)
        resultsFound = areResultsGood()
        searchD = False
    elif searchD:
        searchMethodD(sTopic)
        resultsFound = areResultsGood()
        searchD = False
    return True;


# Start of program
def programStart(searchTopic, searchType, searchInput):
    print("|preparing...")
    searchesDone = False
    while not searchesDone:
        searchesDone = search(searchTopic, searchType, searchInput)

#def main():
#    global inFile
 #   input_file = open(inFile, "r")
  #  aSearchTerm = input_file.read()
   # input_file.close()
    #programStart(aSearchTerm, 'd', 0)

# Tests
# printResults()
print("|started")
input_file = open(inFile, "r")
aSearchTerm = input_file.read()
print("|searching for words like <" + aSearchTerm + ">...")
input_file.close()
programStart(aSearchTerm, 'd', 0)
print("|finished")
