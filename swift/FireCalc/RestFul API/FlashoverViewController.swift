//
//  DetailViewController.swift
//  JOSNSwiftDemo
//
//  Created by Shinkangsan on 1/23/17.
//  Copyright © 2017 Charmal. All rights reserved.
//

import UIKit

class FlashoverViewController: UIViewController {
    
    @IBOutlet weak var textbox1: UITextField!
    @IBOutlet weak var dropdown1: UIPickerView!
    
    @IBOutlet weak var textbox2: UITextField!
    @IBOutlet weak var dropdown2: UIPickerView!
    
    @IBOutlet weak var textbox3: UITextField!
    @IBOutlet weak var dropdown3: UIPickerView!
    
    @IBOutlet weak var textbox4: UITextField!
    @IBOutlet weak var dropdown4: UIPickerView!
    
    @IBOutlet weak var textbox5: UITextField!
    @IBOutlet weak var dropdown5: UIPickerView!
    
    @IBOutlet weak var textbox6: UITextField!
    @IBOutlet weak var dropdown6: UIPickerView!
    
    @IBOutlet weak var textbox7: UITextField!
    @IBOutlet weak var dropdown7: UIPickerView!
    
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
    var materials = ["Aerated Concrete","Alumina Silicate Block","Aluminum (pure)","Brick","Brick / Concrete Block","Calcium Silicate Board","Chipboard","Concrete","Expended Polystyrene"," Fiber Insulation Board","Glass Fiber Insulation","Glass Plate","Gypsum Board","Plasterboard","Plywood","Steel (0.5% Carbon)"]
    
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
    
    func getMaterial(textField:UITextField )-> String {
       
        if textField.text == "Alumina Silicate Block" {
            return "1"
        }else if textField.text == "Aluminum (pure)"
        {
            return "2"
        }else if textField.text == "Brick"
        {
            return "3"
        }else if textField.text == "Brick / Concrete Block"
        {
            return "4"
        }
        else if textField.text == "Calcium Silicate Board"
        {
            return "5"
        }
        else if textField.text == "Chipboard"
        {
            return "6"
        }
        else if textField.text == "Concrete"
        {
            return "7"
        }else if textField.text == "Expended Polystyrene"
        {
            return "8"
        }else if textField.text == "Fiber Insulation Board"
        {
            return "9"
        }else if textField.text == "Glass Fiber Insulation"
        {
            return "10"
        }else if textField.text == "Glass Plate"
        {
            return "11"
        }else if textField.text == "Gypsum Board"
        {
            return "12"
        }else if textField.text == "Plasterboard"
        {
            return "13"
        }else if textField.text == "Plywood"
        {
            return "14"
        }
        else if textField.text == "Steel (0.5% Carbon)"
        {
            return "15"
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
        
        flashover.compartmentWidthMeasure =  getScale(textField: textbox1)
        flashover.compartmentLengthMeasure = getScale(textField: textbox2)
        flashover.compartmentHeightMeasure = getScale(textField: textbox3)
        flashover.ventWidthMeasure = getScale(textField: textbox4)
        flashover.ventHeightMeasure  = getScale(textField: textbox5)
        flashover.liningThicknessMeasure = getScale(textField: textbox5)
        
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
                        
                        flashResponse.Av = response["Av"].string
                        
                        flashResponse.InteriorLiningThermalConductivity = response["InteriorLiningThermalConductivity"].float
                        
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
        
        textbox1.text = list[3]
        textbox2.text = list[1]
        textbox3.text = list[1]
        textbox4.text = list[1]
        textbox5.text = list[1]
        textbox6.text = materials[12]
        textbox7.text = list[2]
        
    }
    
    @IBAction func btnClear(_ sender: UIButton) {
        
        self.txtCompartWidth.text = ""
        self.txtCompartLength.text = ""
        self.txtCompartHeight.text = ""
        self.txtVentWidth.text = ""
    }
    
    @IBAction func btnReference(_ sender: UIButton) {
        
        let url = URL(string: "http://ogneborec.su/files/uploads/files/0460561_8A68C_sfpe_handbook_of_fire_protection_engineering.pdf")!
        
        if UIApplication.shared.canOpenURL(url) {
            if #available(iOS 10.0, *) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            } else {
                // Fallback on earlier versions
            }
            //If you want handle the completion block than
            if #available(iOS 10.0, *) {
                UIApplication.shared.open(url, options: [:], completionHandler: { (success) in
                    print("Open url : \(success)")
                })
            } else {
                // Fallback on earlier versions
            }
        }
    }
    
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        var countrows : Int = list.count
        if pickerView == dropdown6 {
            
            countrows = self.materials.count
        }
        
        return countrows
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == dropdown6 {
            
            let titleRow = materials[row]
            
            return titleRow
            
        }
            
        else {
            let titleRow = list[row]
            
            return titleRow
        }
        
        return ""
    }
    
    
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView == dropdown1 {
            self.textbox1.text = self.list[row]
            self.dropdown1.isHidden = true
        }
            
        else if pickerView == dropdown2{
            self.textbox2.text = self.list[row]
            self.dropdown2.isHidden = true
            
        }
        else if pickerView == dropdown3{
            self.textbox3.text = self.list[row]
            self.dropdown3.isHidden = true
            
        }
        else if pickerView == dropdown4{
            self.textbox4.text = self.list[row]
            self.dropdown4.isHidden = true
            
        }
        else if pickerView == dropdown5{
            self.textbox5.text = self.list[row]
            self.dropdown5.isHidden = true
            
        }
        else if pickerView == dropdown6{
            self.textbox6.text = self.materials[row]
            self.dropdown6.isHidden = true
            
        }
        else if pickerView == dropdown7{
            self.textbox7.text = self.list[row]
            self.dropdown7.isHidden = true
            
        }
    }
    
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if (textField == self.textbox1){
            self.dropdown1.isHidden = false
            
        }
        else if (textField == self.textbox2){
            self.dropdown2.isHidden = false
            
        }
        else if (textField == self.textbox3){
            self.dropdown3.isHidden = false
            
        }
        else  if (textField == self.textbox4){
            self.dropdown4.isHidden = false
            
        }
        else  if (textField == self.textbox5){
            self.dropdown5.isHidden = false
        }
        else  if (textField == self.textbox6){
            self.dropdown6.isHidden = false
            
        }
        else  if (textField == self.textbox7){
            self.dropdown7.isHidden = false
            
        }
        
    }
}

