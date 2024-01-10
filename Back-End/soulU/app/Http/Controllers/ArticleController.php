<?php

namespace App\Http\Controllers;

use App\Models\Article;
use Illuminate\Http\Request;
use App\Http\Resources\ArticleResource;

class ArticleController extends Controller
{
    public function index(){
        $Articles=Article::all();
        // return response()->json(['data' => $Articles]);

        //collection mengembalikan array data
        return ArticleResource::collection($Articles);
    }
}
