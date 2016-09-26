#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import argparse
import http.server
import socketserver


class Handler(http.server.BaseHTTPRequestHandler):
    def __init__(self, *args):
        self.user = None
        super(http.server.BaseHTTPRequestHandler, self).__init__(*args)

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.end_headers()

    def do_POST(self):
        req = self.rfile.read(int(self.headers['Content-Length']))
        print(req.decode("UTF-8"))
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.send_header("Content-Length", len(req))
        self.end_headers()
        self.wfile.write(req)


class MultiThreadedHTTPServer(socketserver.ThreadingMixIn, http.server.HTTPServer):
    def __init__(self, server_address, RequestHandlerClass):
        super(MultiThreadedHTTPServer, self).__init__(server_address, RequestHandlerClass, True)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--server_address', action='store', dest='server_address', help='Server address',
                        default='127.0.0.1')
    parser.add_argument('--server_port', action='store', dest='server_port', help='Server port', default=8080, type=int)
    options = parser.parse_args()
    MultiThreadedHTTPServer((options.server_address, options.server_port), Handler).serve_forever()


if __name__ == '__main__':
    main()