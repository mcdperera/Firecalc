//
//  FlashoverPopViewController.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/12/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//
import UIKit

class HrrPopViewController: UIViewController {
    
    @IBOutlet weak var lblm: UILabel!
    @IBOutlet weak var lbldHC: UILabel!
    @IBOutlet weak var lblQ: UILabel!
    @IBOutlet weak var lblAream2: UILabel!
    @IBOutlet weak var lblAreaf2: UILabel!
    
    var hrrModel = Struct_HrrModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.updateUI()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateUI() {
        
        self.lblm.text = hrrModel.m
        self.lbldHC.text = hrrModel.dHc
        self.lblQ.text = hrrModel.Q
        self.lblAream2.text = hrrModel.AreaM2
        self.lblAreaf2.text = hrrModel.AreaF2
        
                //        self.lblThermalConductivity.text = NSString(format: "%.5f", flashoverModel.InteriorLiningThermalConductivity!) as String + " kW/m-K"
       
    }
    
    @IBAction func btnDismiss(_ sender: UIButton) {
        _ = navigationController?.popToRootViewController(animated: true)
    }
}



