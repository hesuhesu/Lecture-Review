import square2, person

def main() :
    print(square2.base)
    print(square2.square(10))

    maria = person.Person('마리아', 20, '서울시 서초구 반포동')
    maria.greeting()

if __name__ == "__main__" :
    main()