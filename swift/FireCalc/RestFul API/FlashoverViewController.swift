//
//  DetailViewController.swift
//  JOSNSwiftDemo
//
//  Created by Shinkangsan on 1/23/17.
//  Copyright © 2017 Charmal. All rights reserved.
//

import UIKit

class FlashoverViewController: UIViewController {
    
    @IBOutlet weak var compartWidthScaleText: UITextField!
    @IBOutlet weak var compartWidthScalePicker: UIPickerView!
    
    @IBOutlet weak var compartLengthScaleText: UITextField!
    @IBOutlet weak var compartLengthScalePicker: UIPickerView!
    
    //    @IBOutlet weak var compartHeightScaleText: UITextField!
    //    @IBOutlet weak var compartHeightScalePicker: UIPickerView!
    //
    //    @IBOutlet weak var ventWidthScaleText: UITextField!
    //    @IBOutlet weak var ventWidthScalePicker: UIPickerView!
    //
    //    @IBOutlet weak var ventHeightScaleText: UITextField!
    //    @IBOutlet weak var ventHeightScalePicker: UIPickerView!
    
    @IBOutlet weak var txtCompartWidth: UITextField!
    
    @IBOutlet weak var txtCompartLength: UITextField!
    
    @IBOutlet weak var txtCompartHeight: UITextField!
    
    @IBOutlet weak var txtVentWidth: UITextField!
    
    @IBOutlet weak var txtVentHeight: UITextField!
    
    @IBOutlet weak var txtLningThickness: UITextField!
    
    @IBOutlet weak var btnCalcuate: UIButton!
    @IBOutlet weak var btnClear: UIButton!
    @IBOutlet weak var btnPractice: UIButton!
    @IBOutlet weak var btnReference: UIButton!
    
    var list = ["cm", "feet", "inches","meters","mm"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.updateUI()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateUI() {
        //self.nameLabel.text = nameString
        //self.dobLabel.text = dobString
    }
    
    func getScale(textField:UITextField )-> String {
        
        if textField.text == "feet" {
            return "1"
        }else if textField.text == "inches"
        {
            return "2"
        }else if textField.text == "meters"
        {
            return "3"
        }else if textField.text == "mm"
        {
            return "4"
        }
        
        return "0"
        
    }
    
    @IBAction func btnCalculate(_ sender: UIButton) {
        
        var flashover : Struct_FlashoverModel = Struct_FlashoverModel()
        flashover.compartmetWidth = txtCompartWidth.text
        flashover.compartmetLength = txtCompartLength.text
        flashover.compartmetHeigth = txtCompartHeight.text
        flashover.ventWidth = txtVentWidth.text
        flashover.ventHeight = txtVentHeight.text
        flashover.liningThickness = txtLningThickness.text
        flashover.liningMaterial = "1"
        
        //        flashover.compartmentWidthMeasure =  getScale(textField: compartWidthScaleText)
        //        flashover.compartmentLengthMeasure = getScale(textField: compartLengthScaleText)
        //        flashover.compartmentHeightMeasure = getScale(textField: compartHeightScaleText)
        //        flashover.ventWidthMeasure = getScale(textField: ventWidthScaleText)
        //        flashover.ventHeightMeasure  = getScale(textField: ventHeightScaleText)
        flashover.liningThicknessMeasure = "1"
        
        IJProgressView.shared.showProgressView(view)
        
        let params = ["CompartmentWidth":flashover.compartmetWidth,
                      "CompartmentLength":flashover.compartmetLength,
                      "CompartmentHeight":flashover.compartmetHeigth,
                      "VentWidth":flashover.ventWidth,
                      "VentHeight":flashover.ventHeight,
                      "InteriorLiningThickness":flashover.liningThickness,
                      "InteriorLiningMaterial":flashover.liningMaterial,
                      
                      "CompartmentWidthMeasure":flashover.compartmentWidthMeasure,
                      "CompartmentLengthMeasure":flashover.compartmentLengthMeasure,
                      "CompartmentHeightMeasure":flashover.compartmentHeightMeasure,
                      "VentWidthMeasure":flashover.ventWidthMeasure,
                      "VentHeightMeasure":flashover.ventHeightMeasure,
                      "InteriorLiningThicknessMeasure":flashover.liningThicknessMeasure
        ]
        
        
        var flashResponse =  Struct_FlashoverModel()
        
        do {
            
            let opt = try HTTP.POST(Constants.FlashoverApi, parameters: params,headers: nil)
            
            opt.start { response in
                
                let data = response.data
                
                if response.error == nil {
                    
                    let str =  NSString(data: data, encoding: String.Encoding.utf8.rawValue)
                    print(str)
                    
                    let response = JSONDecoder(data)
                    
                    DispatchQueue.main.async(execute: { () -> Void in
                        
                        IJProgressView.shared.hideProgressView()
                        
                        print(response["McCaffrey"].string)
                        print(response["Av"].string)
                        
                        flashResponse.Av = response["Av"].string
                        flashResponse.InteriorLiningThermalConductivity = response["Av"].string
                        flashResponse.At = response["At"].string
                        flashResponse.hk  = response["hk"].string
                        
                        flashResponse.McCaffrey = response["McCaffrey"].string
                        flashResponse.McCaffreyBtu    = response["McCaffreyBtu"].string
                        flashResponse.Babrauskas = response["Babrauskas"].string
                        flashResponse.BabrauskasBtu = response["BabrauskasBtu"].string
                        flashResponse.Thomas   = response["Thomas"].string
                        flashResponse.ThomasBtu   = response["ThomasBtu"].string
                        
                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                        let vc = storyboard.instantiateViewController(withIdentifier: "FlashoverPopViewController") as! FlashoverPopViewController
                        vc.flashoverModel = flashResponse
                        self.navigationController?.pushViewController(vc, animated: true)
                        
                    })
                }
            }
        } catch let error {
            print("got an error creating the request: \(error)")
        }
    }
    
    @IBAction func btnPractice(_ sender: UIButton) {
        
        txtCompartWidth.text = "8"
        txtCompartLength.text = "12"
        txtCompartHeight.text = "7"
        
        txtVentWidth.text = "12"
        txtVentHeight.text = "6"
        
        txtLningThickness.text = "0.5"
    }
    
    @IBAction func btnClear(_ sender: UIButton) {
        
        self.txtCompartWidth.text = ""
        self.txtCompartLength.text = ""
        self.txtCompartHeight.text = ""
        self.txtVentWidth.text = ""
    }
    
    
    public func numberOfComponents(in pickerView: UIPickerView) -> Int{
        return 1
        
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int{
        
        return list.count
        
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        self.view.endEditing(true)
        return list[row]
        
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        
        print(component)
        
        if pickerView == self.compartWidthScalePicker {
            self.compartWidthScaleText.text = self.list[row]
            self.compartWidthScalePicker.isHidden = true
        }
        
        if pickerView == self.compartLengthScaleText {
            self.compartLengthScaleText.text = self.list[row]
            self.compartLengthScalePicker.isHidden = true
        }
        //
        //        self.compartHeightScaleText.text = self.list[row]
        //        self.compartHeightScalePicker.isHidden = true
        //
        //        self.ventWidthScaleText.text = self.list[row]
        //        self.ventWidthScalePicker.isHidden = true
        //
        //        self.ventHeightScaleText.text = self.list[row]
        //        self.ventHeightScalePicker.isHidden = true
        
        
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        
        if compartWidthScaleText == self.compartWidthScaleText {
            self.compartWidthScalePicker.isHidden = false
            compartWidthScaleText.endEditing(true)
        }
        
        
        if compartLengthScaleText == self.compartLengthScaleText {
            self.compartLengthScalePicker.isHidden = false
            compartLengthScaleText.endEditing(true)
        }
        //
        //        if compartHeightScaleText == self.compartHeightScaleText {
        //            self.compartHeightScalePicker.isHidden = false
        //            compartHeightScaleText.endEditing(true)
        //        }
        //
        //        if ventWidthScaleText == self.ventWidthScaleText {
        //            self.ventWidthScalePicker.isHidden = false
        //            ventWidthScaleText.endEditing(true)
        //        }
        //
        //        if ventHeightScaleText == self.ventHeightScaleText {
        //            self.ventHeightScalePicker.isHidden = false
        //            ventHeightScaleText.endEditing(true)
        //        }
    }
}

