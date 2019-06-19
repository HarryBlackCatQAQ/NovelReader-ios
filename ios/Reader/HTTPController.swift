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
    func onSearch(url:String) -> String{
        var res:String = "9"
        
        Alamofire.request(url,method: .post).responseJSON{ (data) in
            if data.result.isSuccess {
 //               res = (self.delegate?.didRecieveResults(results: data.result.value as AnyObject))!
//                print("===" + res)
            }else{
                print("DATA获取失败")
            }
        }
        
        
        //print("===" + res)
        return res
    }
}

protocol HttpProtocol {
    func didRecieveResults(results:AnyObject) -> String
}
