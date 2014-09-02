/**
 * Created by mac on 14-5-14.
 */
 $(function(){
            //国家自动完成
            $("#country").typeahead({
                source: function(query, callback){
                    $.get($hp.ctxpath+'/system.Countrys/search', {
                        keyword: query
                    }, function(data){
                        callback(data);
                    });
                },
                updater:function(item){
                    var itemArr = item.split("-");
                    $("#countryCode").val(itemArr[0]);
                    return itemArr[2];
                }
            });

            //报关信息自动完成
            $("#sku").typeahead({
                source: function(query, callback){
                    $.get($hp.ctxpath+'/order.OrderCustomss/searchSku', {
                        keyword: query
                    }, function(data){
                        callback(data);
                    });
                },
                updater:function(item){
                    var thisDom = this.$element[0];
                    var itemArr = item.split("--");
                    return itemArr[0];
                }
            });
        });
