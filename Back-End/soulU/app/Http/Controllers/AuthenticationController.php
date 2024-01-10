<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Storage;
use Illuminate\Validation\ValidationException;

class AuthenticationController extends Controller
{
    public function login(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);

        $user = User::where('email', $request->email)->first();

        if (!$user || !Hash::check($request->password, $user->password)) {
            throw ValidationException::withMessages([
                'email' => ['email tidak ditemukan']
            ]);
        }
        $accessToken = $user->createToken('user login')->plainTextToken;
        return response()->json([
            "username" => $user->username,
            "token" => $accessToken]
        );
    }

    public function register(Request $request)
    {
        $validate = $request->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);
        $image = null;
        if ($request->file) {
            $fileName=$this->genRandomString();
            $extension=$request->file->extension();
            $image=$fileName.".".$extension;

            Storage::putFileAs('images',$request->file,$image);
        }
        $request['image']=$image;
        $user=User::create([
            'username'=>$request->username,
            'email'=>$request->email,
            'password'=>Hash::make($request->password),
            'firstname'=>$request->firstname,
            'lastname'=>$request->lastname,
            'image'=>$image,
            'level'=>0
        ]);
        return response()->json([
            'message'=>'user berhasil dibuat',
            "username" => $user->username,
        ]);
    }
    function genRandomString($length = 30)
    {
        if ($length < 1)
            $length = 1;
        return substr(preg_replace("/[^A-Za-z0-9]/", '', base64_encode(openssl_random_pseudo_bytes($length * 2))), 0, $length);
    }
}
