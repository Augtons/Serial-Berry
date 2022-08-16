import sys

def gen(raw):
    if("\\" in raw):
        print(f'将unicode \'{raw}\' 转为中文\n')
        print(f'结果:\t', end='')
        eval(f"""    print("{raw}")""")
    else:
        print(f'将中文 \'{raw}\' 转为unicode\n')
        print(f'结果({raw}):\t', end='')
        out = raw.encode('unicode-escape')
        print(str(out)[2:-1].replace('\\\\u', '\\u'))

def interactive_mode():
    while True:
        str = input('>>')
        if str in ('exit', 'quit', 'q'):
            sys.exit(0)
        else:
            gen(str)


if('-h' in sys.argv or '--help' in sys.argv):
    print('\n')
    print("Usage1: u2c.py [string]")
    print("Automatically convert UTF-8 and Unicode-escape to each other")
    print('\n' + '-' * 30 + '\n')
    print("Usage2: u2c.py -i")
    print("Interactive mode")
    print('\n')
    sys.exit(0)

if('-i' in sys.argv):
    print("Interactive mode")
    interactive_mode()

if(len(sys.argv) >= 2):
    raw = sys.argv[1]
    gen(raw)

print("\n")