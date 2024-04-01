import random


def generateString(regex):
    string = ""
    i = 0
    while i < len(regex):
        if (regex[i] == "(" and regex.index(")", i) == len(regex) - 1) or (
                regex[i] == "(" and regex[regex.index(")", i) + 1] not in ["*", "+", "?", "{"]):
            char = random.choice(options(regex[i + 1:regex.index(")", i)]))
            string += char
            i = regex.index(")", i)
            print(f"Adding {char} to string - {string}")

        elif regex[i] == "(" and regex[regex.index(")", i) + 1] == "+":
            times = random.randint(1, 5)
            for _ in range(times):
                char = random.choice(options(regex[i + 1:regex.index(")", i)]))
                string += char
                print(f"Adding {char} to string - {string}")
            i = regex.index(")", i) + 1

        elif regex[i] == "(" and regex[regex.index(")", i) + 1] == "*":
            for _ in range(random.randint(0, 5)):
                char = random.choice(options(regex[i + 1:regex.index(")", i)]))
                string += char
                print(f"Adding {char} to string - {string}")
            i = regex.index(")", i) + 1

        elif regex[i] == "(" and regex[regex.index(")", i) + 1] == "{":
            for _ in range(int(regex[regex.index("{", i) + 1])):
                char = random.choice(options(regex[i + 1:regex.index(")", i)]))
                string += char
                print(f"Adding {char} to string - {string}")
            i = regex.index("}", i) + 1

        elif regex[i] == "(" and regex[regex.index(")", i) + 1] == "?":
            if random.randint(0, 1):
                char = random.choice(options(regex[i + 1:regex.index(")", i)]))
                string += char
                print(f"Adding {char} to string - {string}")
            i = regex.index(")", i) + 1

        elif i < len(regex) - 2 and regex[i + 1] == "?":
            if random.randint(0, 1):
                string += regex[i]
                print(f"Adding {regex[i]} to string - {string}")
            i += 2



        elif regex[i] in '(){|+*?}':
            i += 1
            pass

        else:
            string += regex[i]
            print(f"Adding {regex[i]} to string - {string}")
            i += 1

    return string

def options(sequence):
    return sequence.split("|")


regex1 = "M?(N){2}(O|P){3}Q*R+"
print(generateString(regex1))

regex2 = "(X|Y|Z){3}8+(9|0){2}"
print(generateString(regex2))

regex3 = "(H|i)(J|K)L*N?"
print(generateString(regex3))