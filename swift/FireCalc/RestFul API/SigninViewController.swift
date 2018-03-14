//
//  SigninViewController.swift
//  RestFul API
//
//  Created by c Mahapatabendige on 3/5/2018.
//

import UIKit

class SigninViewController: UIViewController {
    
    @IBOutlet weak var textFieldPassword: UITextField!
    @IBOutlet weak var textFieldemail: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    @IBAction func btnBack(_ sender: UIButton) {
        
        performSegue(withIdentifier: "segue_to_register", sender: nil)
        
    }
    
    @IBAction func btnLogin(_ sender: UIButton) {
        
        var user : Struct_User = Struct_User()
        user.email = textFieldemail.text
        user.password = textFieldPassword.text
        
        if textFieldemail.text != "" && textFieldemail.text != ""{
            
            IJProgressView.shared.showProgressView(view)
            let syn = AsynUser(view: self,user: user)
            syn.login()
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}

