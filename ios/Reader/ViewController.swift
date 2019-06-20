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
    var arrTitle:[String] = [String]()
    var arrAuthor:[String] = [String]()
    var arrImage = [UIImage]()
    var isSearch:Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        sb.delegate = self
    }
    
    func GetMes(bookName:String){
        Alamofire.request(ServerUrl, method: .get,  parameters: ["content":bookName]).responseJSON{ (data) in
            if data.result.isSuccess {
                
                
                var jsonData:JSON
                jsonData = (self.didRecieveResults(results: data.result.value as AnyObject))
                
                //print("count:\(jsonData.count)")
                
                let num:Int = jsonData.count
                var ImgUrl = ""
                for index in 0...(num-1){
                    self.arrTitle.append(jsonData[index]["name"].stringValue)
                    self.arrAuthor.append(jsonData[index]["author"].stringValue)
                    
                    ImgUrl = (jsonData[index]["imageUrl"].stringValue)
                    
                    print(ImgUrl)
                    let url = URL(string: ImgUrl)
                    print(url as Any)
                    if(url != nil){
                        let data = try? Data(contentsOf: url!)
                        let image = UIImage(data: data!)
                        self.arrImage.append(image!)
                    }else{
                        self.arrImage.append(UIImage(named:"null.gif")!)
                    }
                }
                self.tv.reloadData()
                
                //print("===" + (self.didRecieveResults(results: data.result.value as AnyObject)))
            }else{
                print("DATA获取失败")
            }
        }
    }
    
    func didRecieveResults(results: AnyObject) -> JSON{
        let json = JSON(results)
        print(json)
        return json
        //let idx = json[0]["name"].stringValue
        //print(idx)
        //return idx;
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
        //cell.imageView!.image = arrImage[index]
        
        return cell
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        isSearch = false
        self.sb.text = ""
        self.sb.resignFirstResponder()
        self.tv.reloadData()
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String){
        onSearch(str: searchText)
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        print("search button")
        onSearch(str: sb.text!)
        self.sb.resignFirstResponder()
    }
    
    func onSearch(str:String){
        isSearch = true
        GetMes(bookName: str)
        self.tv.reloadData()
    }
}

