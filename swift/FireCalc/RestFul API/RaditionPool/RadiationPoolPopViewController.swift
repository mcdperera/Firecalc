//
//  FlashoverPopViewController.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/12/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//
import UIKit

class RadiationPoolPopViewController: UIViewController {
    
    @IBOutlet weak var lblvalidity: UILabel!
    @IBOutlet weak var lblHeatflux: UILabel!
    @IBOutlet weak var lblHeatfluxBtu: UILabel!
    
    var radiationPoolResponse = Struct_RadiationpoolModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.updateUI()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateUI() {
        
        self.lblvalidity.text = radiationPoolResponse.Vaidity
        self.lblHeatflux.text = radiationPoolResponse.Heatflux
        self.lblHeatfluxBtu.text = radiationPoolResponse.HeatfluxBtu
        
    }
    
    @IBAction func btnDismiss(_ sender: UIButton) {
        _ = navigationController?.popToRootViewController(animated: true)
    }
}




