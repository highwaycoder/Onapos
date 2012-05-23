CC=g++
LD=g++
LDFLAGS=
CFLAGS=-Iinclude

onapos: Main.o Collection.o
	$(LD) -o onapos Main.o Collection.o $(LDFLAGS)

Main.o: Main.cpp
	$(CC) -o Main.o -c Main.cpp $(CFLAGS)

Collection.o: Collection.cpp include/Collection.hpp
	$(CC) -o Collection.o -c Collection.cpp $(CFLAGS)