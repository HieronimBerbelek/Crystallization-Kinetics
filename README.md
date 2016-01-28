# Crystallization-Kinetics

This is little java SE program, which does nonisothermal crystallization kinetics calculations of Avrami, Ozawa, Mo etc. models/methods for experimental DSC data. Me and other students used this for our Chemistry Engineering dissertations. 

IMPORTANT: I am well aware that this project has some object oriented paradigms violated, especially, there's too many hard coupling. Besides, I should comment and test more. In my defence I can say that i had too short deadline to worry about abstraction, and well, program works pretty ok.

I hope eventually I will refractor and improve it.


## Instruction

Program is designed to load txt files of data exported from Proteus Analysis software. File should consist of single dsc crystallization peak data, you have to manually choose the range of it, while exporting. Simple GUI offers options to load data into datalist, open/save datalists, and use its content to proceed with calculations. 

example input txt file can be found in resources/test folder.

Output is saved as txt file, consisting of basic results, like rate coefficients, peak temperatures etc., and extended results, which usually contains plot data, which can be used to draw plots for each model.

I recommend to get some basic knowledge about each crystallization model, before use. 

## Tests

I used only basic JUnit 4 tests, with input from "resources" and mock data structure i wrote;
