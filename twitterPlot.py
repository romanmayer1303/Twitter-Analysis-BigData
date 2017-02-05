import sys
import os
import matplotlib.pyplot as plt
import numpy as np

def numbersToDate(numberArray):
	dateArray = []
	for i in range(len(numberArray)):
		dateArray.append(str(numberArray[i])[0:4]+"/"+str(numberArray[i])[4:6]+"/"+str(numberArray[i])[6:])
	
	return dateArray

def plotTimeEvolution(directoryName, title):

	print("Plotting content of " + directoryName)
	
	occurrences = []
	times = []

	for filename in os.listdir(directoryName):
		if not "c" in filename:
			for line in open(filename, "r",encoding='utf-8', errors='ignore'):
				try:
					entry = line.replace(")", "").replace("(", "")
					times.append(int(entry.split(",")[0]));
					occurrences.append(int(entry.split(",")[1]));
				except ValueError:
					print(filename +" in " +directoryName)
					print(entry)
				
	occurrences = [x for (y,x) in sorted(zip(times,occurrences))]
	times = sorted(times)
	
	cumulatedOccurrences = []
	for i in range(len(occurrences)):
		cumulatedOccurrences.append(sum(occurrences[0:i]))

	fig, axes = plt.subplots(1, 1, figsize=(20,20))
	fig.suptitle(title)
	
	axes.plot(times, occurrences)
	axes.set_xlabel("Date")
	axes.set_ylabel("Number of occurrences")
	axes.set_title("Uses per day")
	axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
	axes.set_xticklabels(numbersToDate(times), rotation="45")
	
	plt.savefig(title+".pdf")
	
	fig, axes = plt.subplots(1, 1, figsize=(20,20))
	fig.suptitle(title)
	
	axes.plot(times, cumulatedOccurrences)
	axes.set_xlabel("Date")
	axes.set_ylabel("Number of occurrences")
	axes.set_title("Cumulated uses")
	axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
	axes.set_xticklabels(numbersToDate(times), rotation="45")

	plt.savefig(title+"Cumulated.pdf")

os.chdir(sys.argv[1])
for directory in os.listdir(sys.argv[1]):
	if os.path.isdir(sys.argv[1]+directory):
		os.chdir(sys.argv[1]+directory)
		#os.system("echo pwd")
		plotTimeEvolution(sys.argv[1]+directory, str(directory))

plt.show()
