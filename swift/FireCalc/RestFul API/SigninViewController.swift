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
        
        //self.tabBarController?.tabBar.isHidden = true
        
        
        
        // Do any additional setup after loading the view.
    }
    
    @IBAction func btnLogin(_ sender: UIButton) {
        
        var user : Struct_User = Struct_User()
        user.email = textFieldemail.text
        user.password = textFieldPassword.text
        
        if textFieldemail.text != "" && textFieldemail.text != ""{
            
            IJProgressView.shared.showProgressView(view)
            //            let syn = AsynUser(view: self,user: user)
            //            syn.login()
            
            let params = ["email":textFieldemail.text!,"password":textFieldPassword.text] as [String : Any]
            do {
                
                let opt = try HTTP.POST(Constants.SignInApi, parameters: params,headers: nil)
                
                opt.start { response in
                    
                    let data = response.data
                    
                    if response.error == nil {
                        let str = NSString(data: data, encoding: String.Encoding.utf8.rawValue)
                        print("response: \(str)") //prints the HTML of the page
                        let jsonUser = JSONDecoder(data)
                        
                        if jsonUser["error"].bool
                        {
                            DispatchQueue.main.async(execute: {
                                ()-> Void in
                                
                                IJProgressView.shared.hideProgressView()
                            }
                            )
                            print("Error")
                        }
                        else{
                            
                            DispatchQueue.main.async(execute: { () -> Void in
                                
                                IJProgressView.shared.hideProgressView()
                                print(jsonUser)
                                
                                
                            })
                        }
                    }
                    
                }
            } catch let error {
                print("got an error creating the request: \(error)")
            }
            
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func hideTabBarButton()
    {
        if let tabBarController = self.tabBarController {
            var viewControllers = tabBarController.viewControllers
            
            viewControllers?.remove(at: 1)
            viewControllers?.remove(at: 1)
            
            tabBarController.viewControllers = viewControllers
        }
    }
}

