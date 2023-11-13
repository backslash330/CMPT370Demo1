# want 1000 -1000

# wiki random page https://en.wikipedia.org/wiki/Special:Random

import requests


def main():
    print("Begin Demo 5")

    link = "https://en.wikipedia.org/wiki/Special:Random"

    r = requests.get(link)
    counts = dict.fromkeys("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 0)
    for line in r.text:
        for char in line:
            if char in counts:
                counts[char] += 1

    # print counts
    for key in counts:
        print("Letter " + str(key) + ": " + str(counts[key]))
    print("End Demo 5")
if __name__ == "__main__":
    main()
