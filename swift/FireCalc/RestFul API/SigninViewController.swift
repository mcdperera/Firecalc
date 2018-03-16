//
//  SigninViewController.swift
//  RestFul API
//
//  Created by c Mahapatabendige on 3/5/2018.
//

import UIKit

class SigninViewController: UIViewController {
    
    
    @IBOutlet weak var textFieldEmail: UITextField!
    
    @IBOutlet weak var textFiledPassword: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    
    @IBAction func btnLogin(_ sender: UIButton) {
        
        let params = ["name":  "name" as String?,
                      "email": textFieldEmail.text,
                      "isAdmin" : "false" as String?,
                      "password": textFiledPassword.text]
        
        if textFieldEmail.text != "" && textFiledPassword.text != ""{
            
            IJProgressView.shared.showProgressView(view)
            
            do {
                
                let opt = try HTTP.POST(Constants.SignInApi, parameters: params,headers: nil)
                
                opt.start { response in
                    
                    if response.error == nil {
                        
                        IJProgressView.shared.hideProgressView()
                        
                    }
                    
                }
            } catch let error {
                print("got an error creating the request: \(error)")
            }
            
        }
        
        let defaults = UserDefaults.standard
        
        defaults.set(textFieldEmail.text, forKey: "username")
        
        defaults.synchronize()
        
        self.textFieldEmail.text  = ""
        self.textFiledPassword.text = ""
        
        IJProgressView.shared.hideProgressView()
        
        let viewController:ViewController = ViewController()
        
        self.present(viewController, animated: true, completion: nil)
        
    }
    
}
