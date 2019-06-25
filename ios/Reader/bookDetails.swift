//
//  bookDetails.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/24.
//  Copyright © 2019 李润泽. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class bookDetails: UIViewController,UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tv: UITableView!
    
    var message:NSMutableDictionary = NSMutableDictionary()
    
    var arrUrl:[String] = [String]()
    var arrTitle:[String] = [String]()
    var arrContent:[String] = [String]()
    var arrIdx:[String] = [String]()
    var arrWebType:[String] = [String]()
    var arrisDownload:[String] = [String]()
    
    let par:NSMutableDictionary = NSMutableDictionary()
    
    override func viewDidLoad() {
        let headers: HTTPHeaders = [
            "Content-Type": "application/json"
        ]
        Alamofire.request("http://localhost:8080/read", method: .post,  parameters: message as! [String:AnyObject], encoding: JSONEncoding(options: []) ,headers:headers).responseJSON{ (data) in
            if data.result.isSuccess {
                var jsonData:JSON
                jsonData = (self.didRecieveResults(results: data.result.value as AnyObject))
                //print(jsonData)
                let num:Int = jsonData.count
                for index in 0...(num-1){
                    self.arrUrl.append(jsonData[index]["url"].stringValue)
                    self.arrTitle.append(jsonData[index]["title"].stringValue)
                    self.arrContent.append(jsonData[index]["content"].stringValue)
                    self.arrIdx.append(jsonData[index]["idx"].stringValue)
                    self.arrWebType.append(jsonData[index]["webType"].stringValue)
                    self.arrisDownload.append(jsonData[index]["download"].stringValue)
                    
                    //print(self.arrTitle[index])
                }
                self.tv.reloadData()
            }else{
                print("DATA获取失败")
            }
        }
    }
    
    func didRecieveResults(results: AnyObject) -> JSON{
        let json = JSON(results)
        //print(json)
        return json
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        print(self.arrTitle.count)
        return self.arrTitle.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell:UITableViewCell = UITableViewCell(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier:"cell")
        let index = indexPath.row

        cell.textLabel!.text = self.arrTitle[index]

        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath){
        self.performSegue(withIdentifier: "bookcontent", sender: self)
        
        print("choose index:\(indexPath.item)") // 选了哪本书
        
        par["url"] = arrUrl[indexPath.item]
        par["title"] = arrTitle[indexPath.item]
        par["content"] = arrContent[indexPath.item]
        par["webType"] = arrWebType[indexPath.item]
        par["idx"] = arrIdx[indexPath.item]
        par["download"] = arrisDownload[indexPath.item]
        
        print(par["title"])
        
        print(par)

        print("=== send succeed too! ===")
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        //页面跳转传值 法1：segue
        let destVc:content = segue.destination as! content
        destVc.message = par
        
    }

}
