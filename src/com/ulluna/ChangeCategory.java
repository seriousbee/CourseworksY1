package com.ulluna;

/**
 * Remember to change the package name after you add it to your project, otherwise it will not work properly
 * Created by PjakProgramowanie on 10/10/16.
 */
public class ChangeCategory {






    String[][] names = {{"C", "K", "F"}, {"cm", "m", "km", "mile", "lata świetlne"}, {"m^3", "l", "dm^3", "galon"}};


    double[][] conversionRates = {{1, 0.04, 50}, {1, 100, 100000, 186000, 120040430}, {1, 100000, 100000, 0.02}};

    
    public String[] getNames(int index){
        return names[index];
    }

}
