//
//  DetailViewController.swift
//  JOSNSwiftDemo
//
//  Created by Shinkangsan on 1/23/17.
//  Copyright © 2017 Charmal. All rights reserved.
//

import UIKit

class HrrViewController: UIViewController {
    
    @IBOutlet weak var textbox1: UITextField!
    @IBOutlet weak var dropdown1: UIPickerView!
    
    @IBOutlet weak var textbox2: UITextField!
    @IBOutlet weak var dropdown2: UIPickerView!
    
    @IBOutlet weak var textbox3: UITextField!
    @IBOutlet weak var dropdown3: UIPickerView!
    
    
    @IBOutlet weak var txtA: UITextField!
    @IBOutlet weak var txtRadius: UITextField!
    
    @IBOutlet weak var btnCalcuate: UIButton!
    @IBOutlet weak var btnClear: UIButton!
    @IBOutlet weak var btnPractice: UIButton!
    
    var list = ["cm", "feet", "inches","meters","mm"]
    
    var materials = ["Cellulose", "Gasoline", "Heptane", "Methanol", "PMMA", "Polyethylene", "Polypropylene", "Polystyrene", "PVC", "Wood"]
    
    var list2Power = [ "ft^2", "inches^2","m^2",]
    
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
    
    func get2Power(textField:UITextField )-> String {
        
        if textField.text == "inches^2" {
            return "1"
        }else if textField.text == "m^2"
        {
            return "2"
        }
        return "0"
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
        
        if textField.text == "Gasoline" {
            return "1"
        }else if textField.text == "Heptane"
        {
            return "2"
        }else if textField.text == "Methanol"
        {
            return "3"
        }else if textField.text == "PMMA"
        {
            return "4"
        }
        else if textField.text == "Polyethylene"
        {
            return "5"
        }
        else if textField.text == "Polypropylene"
        {
            return "6"
        }
        else if textField.text == "Polystyrene"
        {
            return "7"
        }else if textField.text == "PVC"
        {
            return "8"
        }else if textField.text == "Wood"
        {
            return "9"
        }
        
        return "0"
    }
    
    @IBAction func btnCalculate(_ sender: UIButton) {
        
        IJProgressView.shared.showProgressView(view)
        
        let params = ["Fuel":getMaterial(textField: textbox1) as String?,
                      "A":txtA.text as String?,
                      "AMeasure":get2Power(textField: textbox2) as String?,
                      "Radius":txtRadius.text as String?,
                      "RadiusMeasure":getScale(textField: textbox3) as String?
        ]
        
        var hrrResponse =  Struct_HrrModel()
        
        do {
            
            let opt = try HTTP.POST(Constants.HrrApi, parameters: params,headers: nil)
            
            opt.start { response in
                
                let data = response.data
                
                if response.error == nil {
                    
                    let str =  NSString(data: data, encoding: String.Encoding.utf8.rawValue)
                    
                    print(str)
                    
                    let response = JSONDecoder(data)
                    
                    DispatchQueue.main.async(execute: { () -> Void in
                        
                        IJProgressView.shared.hideProgressView()
                        
                        hrrResponse.AreaF2 = response["AreaF2"].string
                        hrrResponse.AreaM2 = response["AreaM2"].string
                        hrrResponse.Q = response["Q"].string
                        hrrResponse.dHc = response["dHc"].string
                        hrrResponse.m = response["m"].string
                        
                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                        
                        let vc = storyboard.instantiateViewController(withIdentifier: "HrrPopViewController") as! HrrPopViewController
                        
                        vc.hrrModel = hrrResponse
                        
                        self.navigationController?.pushViewController(vc, animated: true)
                        
                    })
                }
            }
        } catch let error {
            print("got an error creating the request: \(error)")
        }
    }
    
    @IBAction func btnPractice(_ sender: UIButton) {
        
        txtA.text = "12.566"
        txtRadius.text = "22"
        
        textbox1.text = materials[1]
        textbox2.text = list2Power[2]
        textbox3.text = list[3]
        
        
        
    }
    
    @IBAction func btnClear(_ sender: UIButton) {
        
        self.txtA.text = ""
        self.txtRadius.text = ""
        self.textbox1.text = ""
        self.textbox2.text = ""
        self.textbox3.text = ""
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        var countrows : Int = list.count
        
        if pickerView == dropdown1 {
            
            countrows = self.materials.count
        }
        else if pickerView == dropdown2 {
            
            countrows = self.list2Power.count
        }
        
        return countrows
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == dropdown1 {
            
            let titleRow = materials[row]
            
            return titleRow
            
        }
        else if pickerView == dropdown2 {
            
            let titleRow = list2Power[row]
            
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
    }
}


