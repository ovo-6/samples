import re, sys

word_counts = dict()

with open(sys.argv[1], 'r') as f:
    for line in f:
        words = re.findall("\w+", line)
        for word in words:
            word = word.lower()
            if word in word_counts:
                word_counts[word] += 1
            else:
                word_counts[word] = 1

for word in word_counts:
    print(str(word_counts[word]) + " " + word)
