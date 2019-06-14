//
//  HTTPController.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/14.
//  Copyright © 2019 李润泽. All rights reserved.
//
import UIKit
import Alamofire
import SwiftyJSON

class HTTPController{
    //定义一个代理
    var delegate:HttpProtocol?
    //接受网址,回调代理的方法,传回数据
    func onSearch(url:String) {
        Alamofire.request(url, method:.post).responseJSON(options: JSONSerialization.ReadingOptions.mutableContainers) { (data) -> Void in
            if data.result.isSuccess {
                self.delegate?.didRecieveResults(results: data.result.value as AnyObject)
            } else {
                print("DATA获取失败")
            }
        }
    }
}

protocol HttpProtocol {
    func didRecieveResults(results:AnyObject)
}
