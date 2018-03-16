//
//  DetailViewController.swift
//  JOSNSwiftDemo
//
//  Created by Shinkangsan on 1/23/17.
//  Copyright © 2017 Charmal. All rights reserved.
//

import UIKit

class RadiationPoolViewController: UIViewController {
    
    @IBOutlet weak var textbox1: UITextField!
    @IBOutlet weak var dropdown1: UIPickerView!
    
    @IBOutlet weak var textbox2: UITextField!
    @IBOutlet weak var dropdown2: UIPickerView!
    
    
    @IBOutlet weak var txtDiameter: UITextField!
    
    @IBOutlet weak var txtDistance: UITextField!
    
    @IBOutlet weak var btnCalcuate: UIButton!
    @IBOutlet weak var btnClear: UIButton!
    @IBOutlet weak var btnPractice: UIButton!
    
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
        
        IJProgressView.shared.showProgressView(view)
        
        let params = ["Diameter":txtDiameter.text,
                      "Distance":txtDistance.text,
                      "DiameterMeasure":getScale(textField: txtDiameter) as String?,
                      "DistanceMeasure":getScale(textField: txtDistance) as String?
        ]
        
        var radiationPoolResponse =  Struct_RadiationpoolModel()
        
        do {
            
            let opt = try HTTP.POST(Constants.RadiationPoolApi, parameters: params,headers: nil)
            
            opt.start { response in
                
                let data = response.data
                
                if response.error == nil {
                    
                    let str =  NSString(data: data, encoding: String.Encoding.utf8.rawValue)
                    
                    print(str)
                    
                    let response = JSONDecoder(data)
                    
                    DispatchQueue.main.async(execute: { () -> Void in
                        
                        IJProgressView.shared.hideProgressView()
                        
                        radiationPoolResponse.Vaidity = response["Vaidity"].string
                        radiationPoolResponse.Heatflux = response["Heatflux"].string
                        radiationPoolResponse.HeatfluxBtu = response["HeatfluxBtu"].string
                        
                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                        
                        let vc = storyboard.instantiateViewController(withIdentifier: "RadiationPoolPopViewController") as! RadiationPoolPopViewController
                        
                        vc.radiationPoolResponse = radiationPoolResponse
                        
                        self.navigationController?.pushViewController(vc, animated: true)
                        
                    })
                }
            }
        } catch let error {
            print("got an error creating the request: \(error)")
        }
    }
    
    @IBAction func btnPractice(_ sender: UIButton) {
        
        txtDiameter.text = "6"
        txtDistance.text = "12"
        
        textbox1.text = list[1]
        textbox2.text = list[1]
        
        
    }
    
    @IBAction func btnClear(_ sender: UIButton) {
        
        self.txtDiameter.text = ""
        self.txtDistance.text = ""
        self.textbox1.text = ""
        self.textbox2.text = ""
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        var countrows : Int = list.count
        
        return countrows
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        let titleRow = list[row]
        
        return titleRow
        
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
        
    }
    
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if (textField == self.textbox1){
            self.dropdown1.isHidden = false
            
        }
        else if (textField == self.textbox2){
            self.dropdown2.isHidden = false
            
        }
        
    }
}



