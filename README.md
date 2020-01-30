# Spionkop 🔴🔴

## Android Liverpool FC news aggregator 🤖.

### Architecture 🏛
3 Modules: App, Article Service and Domain

The app dependency model follows a basic implementation of Clean Arch.


  app     ---------->     domain    <----------   service   
  

The app gradle implements the service layer only for DI instantiation, so api is used instead of implementation. This ensures the separation of concerns.

### Testing 👾
Tests are written for each module. This gives examples of both unit tests and android espresso. Mocking network responses, injecting test mocks with DI and stubbed interfaces.

### FP ƛ
I've also played around with some concepts from FP: 
[functional composition](https://github.com/Lenki/spionkop/blob/master/spionkoparticledomain/src/main/java/uk/co/khaleelfreeman/spionkoparticledomain/util/DateUtil.kt) and [functions returning functions](https://github.com/Lenki/spionkop/blob/master/app/src/main/java/uk/co/khaleelfreeman/spion/ui/MainActivity.kt)(the fade function is reused to make fadeIn and fadeOut)


#### Please feel free to download this repo 🤘
