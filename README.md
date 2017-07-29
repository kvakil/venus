# [venus](https://kvakil.github.io/venus/)
[![Build Status](https://travis-ci.com/kvakil/venus.svg?token=ke1yhth1Tq9yPQc4KzUY&branch=master)](https://travis-ci.com/kvakil/venus)

__venus__ is a RISC-V instruction set simulator built for education.

## Using venus

venus is [available online](https://kvakil.github.io/venus/).

## Roadmap

### Supported

* RV32IM (except cycle counters and fence instructions)
* Single-step debugging with undo feature
* View machine code and original instructions side-by-side
* Several `ecall`s: including `print` and `sbrk`
* `la` and `l`oad pseudoinstructions
* Memory visualization

### Currently in progress

* All standard RV32I pseudoinstructions
* Improved integrated editor (syntax highlighting, autocomplete, better error highlighting)
* RV32F support
* Breakpoint debugging

### Long-term Goals

* Variable-length instructions (and RV32C extension)
* Instruction DSL
* RV32G support
* Hardware and cache visualizations
* RV64I support

## Contributing

See `CONTRIBUTING.md` in the root directory.