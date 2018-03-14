//
//  AsyncFlashover.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/11/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//

//
//  AsynUser.swift
//  RestFul API
//
//  Created by phong on 9/10/16.
//  Copyright © 2016 tpcreative.co. All rights reserved.
//

import UIKit

class AsynFlashover: NSObject {
    
    var view : AnyObject?
    var flashover : Struct_FlashoverModel?
    var flashoverDefault : UserDefaults?
    
    init(view : AnyObject, flashover : Struct_FlashoverModel){
        
        self.view = view
        self.flashover = flashover
        self.flashoverDefault  = UserDefaults()
    }
    
    func calculate()   -> (Struct_FlashoverModel) {
        
        
        let params = ["CompartmentWidth":flashover!.compartmetWidth,
                      "CompartmentLength":flashover!.compartmetLength,
                      "CompartmentHeight":flashover!.compartmetHeigth,
                      "VentWidth":flashover!.ventWidth,
                      "VentHeight":flashover!.ventHeight,
                      "InteriorLiningThickness":flashover!.liningThickness,
                      "InteriorLiningMaterial":flashover!.liningMaterial,
                      
                      "CompartmentWidthMeasure":flashover!.compartmentWidthMeasure,
                      "CompartmentLengthMeasure":flashover!.compartmentLengthMeasure,
                      "CompartmentHeightMeasure":flashover!.compartmentHeightMeasure,
                      "VentWidthMeasure":flashover!.ventWidthMeasure,
                      "VentHeightMeasure":flashover!.ventHeightMeasure,
                      "InteriorLiningThicknessMeasure":flashover!.liningThicknessMeasure
        ]
        
        
        var flashResponse =  Struct_FlashoverModel()
        
        do {
            
            let opt = try HTTP.POST("http://10.230.18.76:3001/api/flashover", parameters: params,headers: nil)
            
            opt.start { response in
                
                let data = response.data
                
                if response.error == nil {
                    
                    _ = NSString(data: data, encoding: String.Encoding.utf8.rawValue)
                    print("response111111")
                    
                    let response = JSONDecoder(data)
                    
                    print(response["error"].bool)
                    
                    //                    if !response["error"].bool
                    //                    {
                    //                        DispatchQueue.main.async(execute: {
                    //                            ()-> Void in
                    //                            IJProgressView.shared.hideProgressView()
                    //                        }
                    //                        )
                    //                    }
                    //                    else{
                    //
                    
                    print("AV")
                    print(response["Av"].string)
                    flashResponse.Av = response["Av"].string
                    //
                    //                        DispatchQueue.main.async(execute: { () -> Void in
                    //
                    //                            IJProgressView.shared.hideProgressView()
                    //
                    //                            print("AV")
                    //                            print(response["Av"].string)
                    //                            flashResponse.Av = response["Av"].string
                    //                            //flashResponse.Av =
                    //                        })
                    
                    //}
                    
                    
                }
            }
            return flashResponse
        } catch let error {
            print("got an error creating the request: \(error)")
        }
        return flashResponse
        
    }
    
}

