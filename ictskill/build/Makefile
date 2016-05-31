# Samoa ICTSkill Makefile

TARGET_PLATFORM	=	2012w44-18
SAMOA_BASE_PATH	=	/usr/samoa
PLATFORM_PATH	=	$(SAMOA_BASE_PATH)/platforms/$(TARGET_PLATFORM)
TOOLCHAIN_PATH	=	$(SAMOA_BASE_PATH)/tools/toolchain2.0
OBJDIR = obj

CC	=	$(TOOLCHAIN_PATH)/bin/arm-linux-gcc

CFLAGS = -g -Os -Wall -std=gnu9x
LDFLAGS =
LIBS = -pthread -lapm -ltoolbox -lchorus -lfloat -lmaps

INCLUDE += \
	-I$(PLATFORM_PATH)/include \
	-I$(PLATFORM_PATH)/include/toolbox \
	-I$(PLATFORM_PATH)/include/chorus \
	-I$(PLATFORM_PATH)/include/maps \
	-I$(TOOLCHAIN_PATH)/lib/gcc-lib/arm-linux-uclibc/3.3.4/include \
	-I$(TOOLCHAIN_PATH)/arm-linux-uclibc/include

LIBPATH	= \
	-L$(TOOLCHAIN_PATH)/arm-linux-uclibc/lib \
	-L$(PLATFORM_PATH)/arm/fs/usr/lib

OBJ = $(addprefix $(OBJDIR)/, main.o)

vpath %.c += ../src

$(OBJDIR)/%.o: %.c
	$(CC) -c $(CFLAGS) $(INCLUDE) $(abspath $<) -o $@

all: ictskill

ictskill: $(OBJ)
	$(CC) $(LDFLAGS) $(LIBPATH) -o out/ictskill $(OBJ) $(LIBS)

clean:
	rm out/*
	rm obj/*

install: ictskill
	cp out/ictskill /data/terminals/samoa/ictskill
