import subprocess
import time
import datetime
import matplotlib.pyplot as plt


times = []
averages = []
input_quantities = [1, 2, 4, 6, 8, 12]

input_index = 0
for inputs in input_quantities:
    new_times_list = []
    print(str(inputs) + " files testing..")
    for repeat in range(1000):

        start = time.time()
        p = subprocess.call((r'C:\Users\User1\Desktop\parser\bin\Debug\parser.exe', str(inputs)))
        new_times_list.append(time.time() - start)

    average = round(sum(new_times_list) / float(len(new_times_list)), 1)
    times.append(new_times_list)
    averages.append(average)
    input_index += 1

file = None
str_file = ""
try:
    file = open("log_tests.txt", "r")
    str_file = file.readlines()
except Exception as exc:
    pass
j = 0
str_file += str("\n\n" + str(datetime.datetime.now()) + "\n")
for input in input_quantities:
    print(input, " files:", averages[j], "sec")
    str_file += (str(str(input) + " inputs: " + str(averages[j]) + " sec\n"))
    j += 1

file = open("log_tests.txt", "w+")
for line in str_file:
    file.write(line)
file.write("_____________________________________")

plt.plot(input_quantities, averages)
plt.show()
