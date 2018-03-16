//
//  FlashoverModel.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/11/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//

import UIKit


struct Struct_FlashoverModel {
    
    var compartmetWidth : String?
    var compartmetLength :String?
    var compartmetHeigth  :String?
    var ventWidth  :String?
    var ventHeight  :String?
    var liningThickness  :String?
    var liningMaterial : String?
    
    var compartmentWidthMeasure  :String?
    var compartmentLengthMeasure  :String?
    var compartmentHeightMeasure  :String?
    var ventWidthMeasure  :String?
    var ventHeightMeasure  :String?
    var liningThicknessMeasure  :String?
    
    var Av  :String?
    var At  :String?
    var InteriorLiningThermalConductivity  :Float?
    var hk  :String?
    var McCaffrey  :String?
    var McCaffreyBtu  :String?
    var Babrauskas  :String?
    var BabrauskasBtu  :String?
    var Thomas  :String?
    var ThomasBtu  :String?
    
    init(){
        
    }
    
    init(flashover : Struct_FlashoverModel)
    {
        self.compartmetWidth = flashover.compartmetWidth
        self.compartmetLength = flashover.compartmetLength
        self.compartmetHeigth = flashover.compartmetHeigth
        self.ventWidth = flashover.ventWidth
        self.ventHeight = flashover.ventHeight
        self.liningThickness = flashover.liningThickness
        self.liningMaterial = flashover.liningMaterial

        self.compartmentWidthMeasure = flashover.compartmentWidthMeasure
        self.compartmentLengthMeasure = flashover.compartmentLengthMeasure
        self.compartmentHeightMeasure = flashover.compartmentHeightMeasure
        self.ventWidthMeasure = flashover.ventWidthMeasure
        self.ventHeightMeasure = flashover.ventHeightMeasure
        self.liningThicknessMeasure = flashover.liningThicknessMeasure
    }
    
}


class FlashoverModel: NSObject {
    
}
