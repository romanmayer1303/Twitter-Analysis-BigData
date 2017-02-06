import sys
import os
import matplotlib.pyplot as plt
import numpy as np

def numbersToDate(numberArray):
	dateArray = []
	for i in range(len(numberArray)):
		dateArray.append(str(numberArray[i])[6:]+"/"+str(numberArray[i])[4:6]+"/"+str(numberArray[i])[0:4])
	
	return dateArray

def plotTimeEvolution(directoryName, title):

	plt.xkcd()

	print("Plotting content of " + directoryName)
	
	occurrences = []
	times = []
	hashtagDictionary = {}
	
	for filename in os.listdir(directoryName):
		hasTwoArguments = False
		hasThreeArguments = False
		if not "SUCCESS" in filename and not ".crc" in filename and not ".pdf" in filename:
			for line in open(filename, "r",encoding='utf-8', errors='ignore'):
				if len(line.split(",")) == 2:
					hasTwoArguments = True
					break
				elif len(line.split(",")) == 3:
					hasThreeArguments = True

			
			if hasTwoArguments:
				print("Reading 2-tuple of " + filename + " in "+ directoryName)			
				for line in open(filename, "r",encoding='utf-8', errors='ignore'):
					try:
						entry = line.replace(")", "").replace("(", "")
						times.append(int(entry.split(",")[0]));
						occurrences.append(int(entry.split(",")[1]));
					except ValueError:
						print(filename +" in " +directoryName)
						print(entry)
			elif hasThreeArguments:
				print("Reading 3-tuple of " + filename + " in "+ directoryName)
				for line in open(filename, "r",encoding='utf-8', errors='ignore'):
					try:
						entry = line.replace(")", "").replace("(", "")
						try:
							if not entry.split(",")[0] in hashtagDictionary:
								hashtagDictionary[entry.split(",")[0]] = [[int(entry.split(",")[1])],[int(entry.split(",")[2])]]
							else:
								hashtagDictionary[entry.split(",")[0]][0].append(int(entry.split(",")[1]));
								hashtagDictionary[entry.split(",")[0]][1].append(int(entry.split(",")[2]));
						except IndexError:
							print(line.split(","))
					except ValueError:
						print(filename +" in " +directoryName)
						print(entry)
				
			
	#TODO
	#Implement stuff for 3 elements per line
	if len(hashtagDictionary) == 0:
		print("Plotting 2-tuple of " +directoryName)
		occurrences = [x for (y,x) in sorted(zip(times,occurrences))]
		times = sorted(times)
		
		cumulatedOccurrences = []
		for i in range(len(occurrences)):
			cumulatedOccurrences.append(sum(occurrences[0:i]))
	
		fig, axes = plt.subplots(1, 1, figsize=(20,20))
		fig.suptitle(r"${}$".format(title), fontsize = 24)
		
		axes.plot(times, occurrences)
		axes.set_xlabel(r"$Date$", fontsize = 16)
		axes.set_ylabel(r"$Number\ of\ occurrences$", fontsize = 16)
		axes.set_title(r"$Uses\ per\ day$", fontsize = 20)
		axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
		axes.set_xticklabels(numbersToDate(times), rotation="45")
		
		plt.savefig(title+".pdf")
		
		fig, axes = plt.subplots(1, 1, figsize=(20,20))
		fig.suptitle(r"${}$".format(title), fontsize = 24)
		
		axes.plot(times, cumulatedOccurrences)
		axes.set_xlabel(r"$Date$", fontsize = 16)
		axes.set_ylabel(r"$Number\ of\ occurrences$", fontsize = 16)
		axes.set_title(r"$Cumulated\ uses$", fontsize = 20)
		axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
		axes.set_xticklabels(numbersToDate(times), rotation="45")
	
		plt.savefig(title+"Cumulated.pdf")
	
	else:
		for key in hashtagDictionary:
			try:
				occurrences = [x for (y,x) in sorted(zip(hashtagDictionary[key][0],hashtagDictionary[key][1]))]
				times = sorted(hashtagDictionary[key][0])
			
				cumulatedOccurrences = []
				for i in range(len(occurrences)):
					cumulatedOccurrences.append(sum(occurrences[0:i]))
			
				fig, axes = plt.subplots(1, 1, figsize=(20,20)) 
				fig.suptitle(r"${}$".format(key), fontsize = 24)
				
				axes.plot(times, occurrences)
				axes.set_xlabel(r"$Date$", fontsize = 16)
				axes.set_ylabel(r"$Number\ of\ occurrences$", fontsize = 16)
				axes.set_title(r"$Uses\ per\ day$", fontsize = 20)
				axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
				axes.set_xticklabels(numbersToDate(times), rotation="45")
				
				plt.savefig(key+".pdf")
				
				fig, axes = plt.subplots(1, 1, figsize=(20,20))
				fig.suptitle(r"${}$".format(key), fontsize = 24)
				
				axes.plot(times, cumulatedOccurrences)
				axes.set_xlabel(r"$Date$", fontsize = 16)
				axes.set_ylabel(r"$Number\ of\ occurrences$", fontsize = 16)
				axes.set_title(r"$Cumulated\ uses$", fontsize = 20)
				axes.set_xticks(np.arange(min(times), max(times)+1, 1.0))
				axes.set_xticklabels(numbersToDate(times), rotation="45")
				
				plt.savefig(key+"Cumulated.pdf")
			except ValueError:
				return
	
os.chdir(sys.argv[1])
for directory in os.listdir(sys.argv[1]):
	if os.path.isdir(sys.argv[1]+directory):
		os.chdir(sys.argv[1]+directory)
		plotTimeEvolution(sys.argv[1]+directory, str(directory))

plt.show()
