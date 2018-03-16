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
    
    @IBOutlet weak var lblBabrauskas: UILabel!
    @IBOutlet weak var lblBabrauskasBtu: UILabel!
    
    
    @IBOutlet weak var lblThomasBtu: UILabel!
    @IBOutlet weak var lblThomas: UILabel!
    
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
        
        self.lblThermalConductivity.text = NSString(format: "%.5f", flashoverModel.InteriorLiningThermalConductivity!) as String + " kW/m-K"
        self.lblhK.text = flashoverModel.hk! + " kW/m-K"
        self.lblAv.text = flashoverModel.Av! + " m^2"
        self.lblAt.text = flashoverModel.At! + " m^2"
        
        
        self.lblMcCaffrey.text = flashoverModel.McCaffrey! //+ " kW"
        self.lblMcCaffreyBtu.text = flashoverModel.McCaffreyBtu!// + "butSec"
        self.lblBabrauskas.text = flashoverModel.Babrauskas! //+ "kW"
        self.lblBabrauskasBtu.text = flashoverModel.BabrauskasBtu! //+ "butSec"
        self.lblThomas.text = flashoverModel.Thomas! //+ "kW"
        self.lblThomasBtu.text = flashoverModel.ThomasBtu! //+ "butSec"
        
    }
    
    @IBAction func btnDismiss(_ sender: UIButton) {
        _ = navigationController?.popToRootViewController(animated: true)
    }
}


