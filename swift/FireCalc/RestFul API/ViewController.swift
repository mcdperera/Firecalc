//
//  DashboardViewController.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/6/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//
import UIKit

class ViewController: UIViewController ,UITableViewDataSource,UITableViewDelegate{
    
    let urlString = "http://10.230.18.76:3001/api/category"
    
    @IBOutlet weak var tableView: UITableView!
    
    var nameArray = [String]()
    var dobArray = [String]()
    var imgURLArray = [String]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.downloadJsonWithURL()
        
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func downloadJsonWithURL() {
        
        let url = NSURL(string: urlString)
        
        URLSession.shared.dataTask(with: (url as URL?)!, completionHandler: {(data, response, error) -> Void in
            
            if let jsonObj = try? JSONSerialization.jsonObject(with: data!, options: .allowFragments) as? NSDictionary {
                
                //print(jsonObj!.value(forKey: "menus"))
                
                if let categoryArray = jsonObj!.value(forKey: "menus") as? NSArray {
                    for category in categoryArray{
                        
                        if let categoryDict = category as? NSDictionary {
                            
                            if let name = categoryDict.value(forKey: "title") {
                                self.nameArray.append(name as! String)
                            }
                            
                            if let name = categoryDict.value(forKey: "image") {
                                let imageUrl = "https://raw.githubusercontent.com/mcdperera/Firecalc/master/services/public/images/" + (name as! String)
                                
                                self.imgURLArray.append(imageUrl )
                                
                            }
                            
                        }
                    }
                }
                
                OperationQueue.main.addOperation({
                    self.tableView.reloadData()
                })
            }
        }).resume()
    }
    
    
    func downloadJsonWithTask() {
        
        let url = NSURL(string: urlString)
        
        var downloadTask = URLRequest(url: (url as URL?)!, cachePolicy: URLRequest.CachePolicy.reloadIgnoringCacheData, timeoutInterval: 20)
        
        downloadTask.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: downloadTask, completionHandler: {(data, response, error) -> Void in
            
            _ = try? JSONSerialization.jsonObject(with: data!, options: .allowFragments)
        }).resume()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return nameArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! TableViewCell
        cell.nameLabel.text = nameArray[indexPath.row]
        
        let imgURL = NSURL(string: imgURLArray[indexPath.row])
        
        if imgURL != nil {
            let data = NSData(contentsOf: (imgURL as URL?)!)
            cell.imgView.image = UIImage(data: data! as Data)
        }
        
        return cell
    }
    
    ///for showing next detailed screen with the downloaded info
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        if indexPath.row == 0 {
            
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "FlashoverViewController") as! FlashoverViewController
                        
            self.navigationController?.pushViewController(vc, animated: true)
        }else
        {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "ConductionViewController") as! ConductionViewController
            
            //let vc = self.storyboard?.instantiateViewController(withIdentifier: "FlashoverViewController") as! FlashoverViewController
            //vc.imageString = imgURLArray[indexPath.row]
            vc.nameString = nameArray[indexPath.row]
            
            self.navigationController?.pushViewController(vc, animated: true)
        }
        
    }
}
