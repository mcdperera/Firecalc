//
//  FlashoverPopViewController.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/12/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//
import UIKit

class FlashoverPopViewController: UIViewController {
    
    @IBOutlet weak var lblThermalConductivity: UILabel!
    @IBOutlet weak var lblhK: UILabel!
    @IBOutlet weak var lblAv: UILabel!
    @IBOutlet weak var lblAt: UILabel!
    @IBOutlet weak var lblMcCaffrey: UILabel!
    @IBOutlet weak var lblMcCaffreyBtu: UILabel!
    
    @IBOutlet weak var lblThomasBtu: UILabel!
    @IBOutlet weak var lblThomas: UILabel!
    @IBOutlet weak var lblBabrauskasBtu: UILabel!
    @IBOutlet weak var lblMBabrauskas: UILabel!
    
    var flashoverModel = Struct_FlashoverModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.updateUI()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateUI() {
        self.lblThermalConductivity.text = flashoverModel.InteriorLiningThermalConductivity! + "kWmK"
        self.lblhK.text = flashoverModel.hk! + "kWmK"
        self.lblAv.text = flashoverModel.Av! + "mm"
         self.lblAt.text = flashoverModel.At! + "mm"
        
        
        //self.lblMcCaffrey.text = flashoverModel.McCaffrey! + "kW"
//        self.lblMcCaffreyBtu.text = flashoverModel.McCaffreyBtu! + "butSec"
//        self.lblMBabrauskas.text = flashoverModel.Babrauskas! + "kW"
//        self.lblBabrauskasBtu.text = flashoverModel.BabrauskasBtu! + "butSec"
//        self.lblThomas.text = flashoverModel.Thomas! + "kW"
//        self.lblThomasBtu.text = flashoverModel.ThomasBtu! + "butSec"
        
    }
    
    @IBAction func btnDismiss(_ sender: UIButton) {
     _ = navigationController?.popToRootViewController(animated: true)
    }
}


