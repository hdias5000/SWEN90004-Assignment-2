max_life_expectancy_values = range(10, 100, 10)

import subprocess
for max_life_exp in max_life_expectancy_values:
    args = [
        "250", # num people
        "5", # max vision
        "15", # metabolism_max
        "1", # life expectancy min
        str(max_life_exp), # life expectancy max
        "0.1", # percent best land
        "1.0", # grain growth interval
        "4", # grain growth rate
        "1000", # time max
        "output/max_exp_{}.csv".format(max_life_exp) # csv filename
    ]
    # print(["python3", "plot.py"] + args)
    subprocess.Popen(["python3", "plot.py"] + args)