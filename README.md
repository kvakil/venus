# [venus](https://kvakil.github.io/venus/)
[![Build Status](https://travis-ci.com/kvakil/venus.svg?token=ke1yhth1Tq9yPQc4KzUY&branch=master)](https://travis-ci.com/kvakil/venus)

__venus__ is a RISC-V instruction set simulator built for education.

## Using venus

venus is [available online](https://kvakil.github.io/venus/).

## Features
* RV32IM
* Single-step debugging with undo feature
* Breakpoint debugging
* View machine code and original instructions side-by-side
* Several `ecall`s: including `print` and `sbrk`
* Memory visualization

## Memeory Map and IO

 * Inspired by MIPS32 and SPIM
 * Code (`.text`) starts at 0x00000000
 * Data (`.data`) starts at 0x10000000
 * ecall like [SPIM's syscalls](https://www.doc.ic.ac.uk/lab/secondyear/spim/node8.html) with support of (ecall ID in a0):
   * 1 - print integer in a1
   * 4 - print null-terminated string whose address is in a1
   * 9 - allocate a1 bytes on the heap (sbrk); returns pointer to beginning of this memory in a0
   * 10 - exit (no cause)
   * 11 - print character in a1
   * 17 - exit with cause (return code in a1)

## Roadmap

See the [roadmap here](https://github.com/kvakil/venus/projects/2).

## Contributing

See `CONTRIBUTING.md` in the root directory.
