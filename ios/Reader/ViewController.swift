//
//  ViewController.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/13.
//  Copyright © 2019 李润泽. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class ViewController: UIViewController,UITableViewDelegate, UITableViewDataSource,UISearchBarDelegate {
    @IBOutlet weak var tv: UITableView!
    @IBOutlet weak var sb: UISearchBar!
    
    let ServerUrl = "http://localhost:8080/searching"
    let par:NSMutableDictionary = NSMutableDictionary()
        
    var arrTitle:[String] = [String]()
    var arrAuthor:[String] = [String]()
    var arrImage = [UIImage]()
    var isSearch:Bool = false
    
    var sendimageUrl:[String] = [String]()
    var sendauthor:[String] = [String]()
    var sendidx:[String] = [String]()
    var sendwebType:[String] = [String]()
    var sendurl:[String] = [String]()
    var sendname:[String] = [String]()
    var sendhtmlContent:[String] = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        sb.delegate = self
    }
    
    func GetMes(bookName:String){
        Alamofire.request(ServerUrl, method: .get,  parameters: ["content":bookName]).responseJSON{ (data) in
            if data.result.isSuccess {
                
                var jsonData:JSON
                jsonData = (self.didRecieveResults(results: data.result.value as AnyObject))
                
                let num:Int = jsonData.count
                var ImgUrl = ""
                for index in 0...(num-1){
                    self.sendimageUrl.append(jsonData[index]["imageUrl"].stringValue)
                    self.sendauthor.append(jsonData[index]["author"].stringValue)
                    self.sendidx.append(jsonData[index]["idx"].stringValue)
                    self.sendwebType.append(jsonData[index]["webType"].stringValue)
                    self.sendurl.append(jsonData[index]["url"].stringValue)
                    self.sendname.append(jsonData[index]["name"].stringValue)
                    self.sendhtmlContent.append(jsonData[index]["htmlContent"].stringValue)
                    
                    self.arrTitle.append(jsonData[index]["name"].stringValue)
                    self.arrAuthor.append(jsonData[index]["author"].stringValue)
                    ImgUrl = (jsonData[index]["imageUrl"].stringValue)
                    let url = URL(string: ImgUrl)
                    if(url != nil){
                        let data = try? Data(contentsOf: url!)
                        let image = UIImage(data: data!)
                        self.arrImage.append(image!)
                    }else{
                        self.arrImage.append(UIImage(named:"null.gif")!)
                    }
                }
                self.tv.reloadData()
            }else{
                print("DATA获取失败")
            }
        }
    }
    
    
    func didRecieveResults(results: AnyObject) -> JSON{
        let json = JSON(results)
        return json
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return arrTitle.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let cell:UITableViewCell = UITableViewCell(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier:"cell")
        let index = indexPath.row
        
        cell.textLabel!.text = arrTitle[index]
        
        cell.detailTextLabel!.text = arrAuthor[index]

        cell.imageView!.image = arrImage[index]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath){
    
        self.performSegue(withIdentifier: "bookdetails", sender: self)
        
        print("choose index:\(indexPath.item)") // 选了哪本书
        
        par["imageUrl"] = sendurl[indexPath.item]
        par["name"] = sendname[indexPath.item]
        par["author"] = sendauthor[indexPath.item]
        par["webType"] = sendwebType[indexPath.item]
        par["idx"] = sendidx[indexPath.item]
        par["url"] = sendurl[indexPath.item]
        par["htmlContent"] = sendhtmlContent[indexPath.item]
        
        print(par)

        print("=== send succeed! ===")

    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        //页面跳转传值 法1：segue
        let destVc:bookDetails = segue.destination as! bookDetails
        destVc.message = par
        
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        isSearch = false
        self.sb.text = ""
        self.sb.resignFirstResponder()
        self.tv.reloadData()
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        onSearch(str: sb.text!)
        self.sb.resignFirstResponder()
    }
    
    func onSearch(str:String){
        isSearch = true
        //GetMes(bookName: str)
        GetMes(bookName: "斗罗大陆")
        self.tv.reloadData()
    }
}

