import random

f = open('./in.txt', 'w')

for i in range(100):
    for j in range (100):
        f.write(random.choice(['0', '1']))
    f.write('\n')

f.close()