!
function(e, t) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = e.document ? t(e, !0) : function(e) {
        if (!e.document) throw new Error("jQuery requires a window with a document");
        return t(e)
    }: t(e)
} ("undefined" != typeof window ? window: this,
function(e, t) {
    function n(e) {
        var t = e.length,
        n = re.type(e);
        return "function" === n || re.isWindow(e) ? !1 : 1 === e.nodeType && t ? !0 : "array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e
    }
    function i(e, t, n) {
        if (re.isFunction(t)) return re.grep(e,
        function(e, i) {
            return !! t.call(e, i, e) !== n
        });
        if (t.nodeType) return re.grep(e,
        function(e) {
            return e === t !== n
        });
        if ("string" == typeof t) {
            if (pe.test(t)) return re.filter(t, e, n);
            t = re.filter(t, e)
        }
        return re.grep(e,
        function(e) {
            return re.inArray(e, t) >= 0 !== n
        })
    }
    function r(e, t) {
        do e = e[t];
        while (e && 1 !== e.nodeType);
        return e
    }
    function o(e) {
        var t = xe[e] = {};
        return re.each(e.match(be) || [],
        function(e, n) {
            t[n] = !0
        }),
        t
    }
    function s() {
        he.addEventListener ? (he.removeEventListener("DOMContentLoaded", a, !1), e.removeEventListener("load", a, !1)) : (he.detachEvent("onreadystatechange", a), e.detachEvent("onload", a))
    }
    function a() { (he.addEventListener || "load" === event.type || "complete" === he.readyState) && (s(), re.ready())
    }
    function l(e, t, n) {
        if (void 0 === n && 1 === e.nodeType) {
            var i = "data-" + t.replace(Ne, "-$1").toLowerCase();
            if (n = e.getAttribute(i), "string" == typeof n) {
                try {
                    n = "true" === n ? !0 : "false" === n ? !1 : "null" === n ? null: +n + "" === n ? +n: Ce.test(n) ? re.parseJSON(n) : n
                } catch(r) {}
                re.data(e, t, n)
            } else n = void 0
        }
        return n
    }
    function c(e) {
        var t;
        for (t in e) if (("data" !== t || !re.isEmptyObject(e[t])) && "toJSON" !== t) return ! 1;
        return ! 0
    }
    function u(e, t, n, i) {
        if (re.acceptData(e)) {
            var r, o, s = re.expando,
            a = e.nodeType,
            l = a ? re.cache: e,
            c = a ? e[s] : e[s] && s;
            if (c && l[c] && (i || l[c].data) || void 0 !== n || "string" != typeof t) return c || (c = a ? e[s] = V.pop() || re.guid++:s),
            l[c] || (l[c] = a ? {}: {
                toJSON: re.noop
            }),
            ("object" == typeof t || "function" == typeof t) && (i ? l[c] = re.extend(l[c], t) : l[c].data = re.extend(l[c].data, t)),
            o = l[c],
            i || (o.data || (o.data = {}), o = o.data),
            void 0 !== n && (o[re.camelCase(t)] = n),
            "string" == typeof t ? (r = o[t], null == r && (r = o[re.camelCase(t)])) : r = o,
            r
        }
    }
    function f(e, t, n) {
        if (re.acceptData(e)) {
            var i, r, o = e.nodeType,
            s = o ? re.cache: e,
            a = o ? e[re.expando] : re.expando;
            if (s[a]) {
                if (t && (i = n ? s[a] : s[a].data)) {
                    re.isArray(t) ? t = t.concat(re.map(t, re.camelCase)) : t in i ? t = [t] : (t = re.camelCase(t), t = t in i ? [t] : t.split(" ")),
                    r = t.length;
                    for (; r--;) delete i[t[r]];
                    if (n ? !c(i) : !re.isEmptyObject(i)) return
                } (n || (delete s[a].data, c(s[a]))) && (o ? re.cleanData([e], !0) : ne.deleteExpando || s != s.window ? delete s[a] : s[a] = null)
            }
        }
    }
    function p() {
        return ! 0
    }
    function d() {
        return ! 1
    }
    function h() {
        try {
            return he.activeElement
        } catch(e) {}
    }
    function g(e) {
        var t = Oe.split("|"),
        n = e.createDocumentFragment();
        if (n.createElement) for (; t.length;) n.createElement(t.pop());
        return n
    }
    function m(e, t) {
        var n, i, r = 0,
        o = typeof e.getElementsByTagName !== _e ? e.getElementsByTagName(t || "*") : typeof e.querySelectorAll !== _e ? e.querySelectorAll(t || "*") : void 0;
        if (!o) for (o = [], n = e.childNodes || e; null != (i = n[r]); r++) ! t || re.nodeName(i, t) ? o.push(i) : re.merge(o, m(i, t));
        return void 0 === t || t && re.nodeName(e, t) ? re.merge([e], o) : o
    }
    function v(e) {
        Pe.test(e.type) && (e.defaultChecked = e.checked)
    }
    function y(e, t) {
        return re.nodeName(e, "table") && re.nodeName(11 !== t.nodeType ? t: t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e.appendChild(e.ownerDocument.createElement("tbody")) : e
    }
    function b(e) {
        return e.type = (null !== re.find.attr(e, "type")) + "/" + e.type,
        e
    }
    function x(e) {
        var t = Ye.exec(e.type);
        return t ? e.type = t[1] : e.removeAttribute("type"),
        e
    }
    function w(e, t) {
        for (var n, i = 0; null != (n = e[i]); i++) re._data(n, "globalEval", !t || re._data(t[i], "globalEval"))
    }
    function T(e, t) {
        if (1 === t.nodeType && re.hasData(e)) {
            var n, i, r, o = re._data(e),
            s = re._data(t, o),
            a = o.events;
            if (a) {
                delete s.handle,
                s.events = {};
                for (n in a) for (i = 0, r = a[n].length; r > i; i++) re.event.add(t, n, a[n][i])
            }
            s.data && (s.data = re.extend({},
            s.data))
        }
    }
    function _(e, t) {
        var n, i, r;
        if (1 === t.nodeType) {
            if (n = t.nodeName.toLowerCase(), !ne.noCloneEvent && t[re.expando]) {
                r = re._data(t);
                for (i in r.events) re.removeEvent(t, i, r.handle);
                t.removeAttribute(re.expando)
            }
            "script" === n && t.text !== e.text ? (b(t).text = e.text, x(t)) : "object" === n ? (t.parentNode && (t.outerHTML = e.outerHTML), ne.html5Clone && e.innerHTML && !re.trim(t.innerHTML) && (t.innerHTML = e.innerHTML)) : "input" === n && Pe.test(e.type) ? (t.defaultChecked = t.checked = e.checked, t.value !== e.value && (t.value = e.value)) : "option" === n ? t.defaultSelected = t.selected = e.defaultSelected: ("input" === n || "textarea" === n) && (t.defaultValue = e.defaultValue)
        }
    }
    function C(t, n) {
        var i, r = re(n.createElement(t)).appendTo(n.body),
        o = e.getDefaultComputedStyle && (i = e.getDefaultComputedStyle(r[0])) ? i.display: re.css(r[0], "display");
        return r.detach(),
        o
    }
    function N(e) {
        var t = he,
        n = Ze[e];
        return n || (n = C(e, t), "none" !== n && n || (Je = (Je || re("<iframe frameborder='0' width='0' height='0'/>")).appendTo(t.documentElement), t = (Je[0].contentWindow || Je[0].contentDocument).document, t.write(), t.close(), n = C(e, t), Je.detach()), Ze[e] = n),
        n
    }
    function E(e, t) {
        return {
            get: function() {
                var n = e();
                return null != n ? n ? void delete this.get: (this.get = t).apply(this, arguments) : void 0
            }
        }
    }
    function k(e, t) {
        if (t in e) return t;
        for (var n = t.charAt(0).toUpperCase() + t.slice(1), i = t, r = pt.length; r--;) if (t = pt[r] + n, t in e) return t;
        return i
    }
    function S(e, t) {
        for (var n, i, r, o = [], s = 0, a = e.length; a > s; s++) i = e[s],
        i.style && (o[s] = re._data(i, "olddisplay"), n = i.style.display, t ? (o[s] || "none" !== n || (i.style.display = ""), "" === i.style.display && Se(i) && (o[s] = re._data(i, "olddisplay", N(i.nodeName)))) : (r = Se(i), (n && "none" !== n || !r) && re._data(i, "olddisplay", r ? n: re.css(i, "display"))));
        for (s = 0; a > s; s++) i = e[s],
        i.style && (t && "none" !== i.style.display && "" !== i.style.display || (i.style.display = t ? o[s] || "": "none"));
        return e
    }
    function D(e, t, n) {
        var i = lt.exec(t);
        return i ? Math.max(0, i[1] - (n || 0)) + (i[2] || "px") : t
    }
    function P(e, t, n, i, r) {
        for (var o = n === (i ? "border": "content") ? 4 : "width" === t ? 1 : 0, s = 0; 4 > o; o += 2)"margin" === n && (s += re.css(e, n + ke[o], !0, r)),
        i ? ("content" === n && (s -= re.css(e, "padding" + ke[o], !0, r)), "margin" !== n && (s -= re.css(e, "border" + ke[o] + "Width", !0, r))) : (s += re.css(e, "padding" + ke[o], !0, r), "padding" !== n && (s += re.css(e, "border" + ke[o] + "Width", !0, r)));
        return s
    }
    function A(e, t, n) {
        var i = !0,
        r = "width" === t ? e.offsetWidth: e.offsetHeight,
        o = et(e),
        s = ne.boxSizing && "border-box" === re.css(e, "boxSizing", !1, o);
        if (0 >= r || null == r) {
            if (r = tt(e, t, o), (0 > r || null == r) && (r = e.style[t]), it.test(r)) return r;
            i = s && (ne.boxSizingReliable() || r === e.style[t]),
            r = parseFloat(r) || 0
        }
        return r + P(e, t, n || (s ? "border": "content"), i, o) + "px"
    }
    function j(e, t, n, i, r) {
        return new j.prototype.init(e, t, n, i, r)
    }
    function L() {
        return setTimeout(function() {
            dt = void 0
        }),
        dt = re.now()
    }
    function H(e, t) {
        var n, i = {
            height: e
        },
        r = 0;
        for (t = t ? 1 : 0; 4 > r; r += 2 - t) n = ke[r],
        i["margin" + n] = i["padding" + n] = e;
        return t && (i.opacity = i.width = e),
        i
    }
    function M(e, t, n) {
        for (var i, r = (bt[t] || []).concat(bt["*"]), o = 0, s = r.length; s > o; o++) if (i = r[o].call(n, t, e)) return i
    }
    function O(e, t, n) {
        var i, r, o, s, a, l, c, u, f = this,
        p = {},
        d = e.style,
        h = e.nodeType && Se(e),
        g = re._data(e, "fxshow");
        n.queue || (a = re._queueHooks(e, "fx"), null == a.unqueued && (a.unqueued = 0, l = a.empty.fire, a.empty.fire = function() {
            a.unqueued || l()
        }), a.unqueued++, f.always(function() {
            f.always(function() {
                a.unqueued--,
                re.queue(e, "fx").length || a.empty.fire()
            })
        })),
        1 === e.nodeType && ("height" in t || "width" in t) && (n.overflow = [d.overflow, d.overflowX, d.overflowY], c = re.css(e, "display"), u = "none" === c ? re._data(e, "olddisplay") || N(e.nodeName) : c, "inline" === u && "none" === re.css(e, "float") && (ne.inlineBlockNeedsLayout && "inline" !== N(e.nodeName) ? d.zoom = 1 : d.display = "inline-block")),
        n.overflow && (d.overflow = "hidden", ne.shrinkWrapBlocks() || f.always(function() {
            d.overflow = n.overflow[0],
            d.overflowX = n.overflow[1],
            d.overflowY = n.overflow[2]
        }));
        for (i in t) if (r = t[i], gt.exec(r)) {
            if (delete t[i], o = o || "toggle" === r, r === (h ? "hide": "show")) {
                if ("show" !== r || !g || void 0 === g[i]) continue;
                h = !0
            }
            p[i] = g && g[i] || re.style(e, i)
        } else c = void 0;
        if (re.isEmptyObject(p))"inline" === ("none" === c ? N(e.nodeName) : c) && (d.display = c);
        else {
            g ? "hidden" in g && (h = g.hidden) : g = re._data(e, "fxshow", {}),
            o && (g.hidden = !h),
            h ? re(e).show() : f.done(function() {
                re(e).hide()
            }),
            f.done(function() {
                var t;
                re._removeData(e, "fxshow");
                for (t in p) re.style(e, t, p[t])
            });
            for (i in p) s = M(h ? g[i] : 0, i, f),
            i in g || (g[i] = s.start, h && (s.end = s.start, s.start = "width" === i || "height" === i ? 1 : 0))
        }
    }
    function W(e, t) {
        var n, i, r, o, s;
        for (n in e) if (i = re.camelCase(n), r = t[i], o = e[n], re.isArray(o) && (r = o[1], o = e[n] = o[0]), n !== i && (e[i] = o, delete e[n]), s = re.cssHooks[i], s && "expand" in s) {
            o = s.expand(o),
            delete e[i];
            for (n in o) n in e || (e[n] = o[n], t[n] = r)
        } else t[i] = r
    }
    function I(e, t, n) {
        var i, r, o = 0,
        s = yt.length,
        a = re.Deferred().always(function() {
            delete l.elem
        }),
        l = function() {
            if (r) return ! 1;
            for (var t = dt || L(), n = Math.max(0, c.startTime + c.duration - t), i = n / c.duration || 0, o = 1 - i, s = 0, l = c.tweens.length; l > s; s++) c.tweens[s].run(o);
            return a.notifyWith(e, [c, o, n]),
            1 > o && l ? n: (a.resolveWith(e, [c]), !1)
        },
        c = a.promise({
            elem: e,
            props: re.extend({},
            t),
            opts: re.extend(!0, {
                specialEasing: {}
            },
            n),
            originalProperties: t,
            originalOptions: n,
            startTime: dt || L(),
            duration: n.duration,
            tweens: [],
            createTween: function(t, n) {
                var i = re.Tween(e, c.opts, t, n, c.opts.specialEasing[t] || c.opts.easing);
                return c.tweens.push(i),
                i
            },
            stop: function(t) {
                var n = 0,
                i = t ? c.tweens.length: 0;
                if (r) return this;
                for (r = !0; i > n; n++) c.tweens[n].run(1);
                return t ? a.resolveWith(e, [c, t]) : a.rejectWith(e, [c, t]),
                this
            }
        }),
        u = c.props;
        for (W(u, c.opts.specialEasing); s > o; o++) if (i = yt[o].call(c, e, u, c.opts)) return i;
        return re.map(u, M, c),
        re.isFunction(c.opts.start) && c.opts.start.call(e, c),
        re.fx.timer(re.extend(l, {
            elem: e,
            anim: c,
            queue: c.opts.queue
        })),
        c.progress(c.opts.progress).done(c.opts.done, c.opts.complete).fail(c.opts.fail).always(c.opts.always)
    }
    function F(e) {
        return function(t, n) {
            "string" != typeof t && (n = t, t = "*");
            var i, r = 0,
            o = t.toLowerCase().match(be) || [];
            if (re.isFunction(n)) for (; i = o[r++];)"+" === i.charAt(0) ? (i = i.slice(1) || "*", (e[i] = e[i] || []).unshift(n)) : (e[i] = e[i] || []).push(n)
        }
    }
    function q(e, t, n, i) {
        function r(a) {
            var l;
            return o[a] = !0,
            re.each(e[a] || [],
            function(e, a) {
                var c = a(t, n, i);
                return "string" != typeof c || s || o[c] ? s ? !(l = c) : void 0 : (t.dataTypes.unshift(c), r(c), !1)
            }),
            l
        }
        var o = {},
        s = e === zt;
        return r(t.dataTypes[0]) || !o["*"] && r("*")
    }
    function B(e, t) {
        var n, i, r = re.ajaxSettings.flatOptions || {};
        for (i in t) void 0 !== t[i] && ((r[i] ? e: n || (n = {}))[i] = t[i]);
        return n && re.extend(!0, e, n),
        e
    }
    function R(e, t, n) {
        for (var i, r, o, s, a = e.contents,
        l = e.dataTypes;
        "*" === l[0];) l.shift(),
        void 0 === r && (r = e.mimeType || t.getResponseHeader("Content-Type"));
        if (r) for (s in a) if (a[s] && a[s].test(r)) {
            l.unshift(s);
            break
        }
        if (l[0] in n) o = l[0];
        else {
            for (s in n) {
                if (!l[0] || e.converters[s + " " + l[0]]) {
                    o = s;
                    break
                }
                i || (i = s)
            }
            o = o || i
        }
        return o ? (o !== l[0] && l.unshift(o), n[o]) : void 0
    }
    function z(e, t, n, i) {
        var r, o, s, a, l, c = {},
        u = e.dataTypes.slice();
        if (u[1]) for (s in e.converters) c[s.toLowerCase()] = e.converters[s];
        for (o = u.shift(); o;) if (e.responseFields[o] && (n[e.responseFields[o]] = t), !l && i && e.dataFilter && (t = e.dataFilter(t, e.dataType)), l = o, o = u.shift()) if ("*" === o) o = l;
        else if ("*" !== l && l !== o) {
            if (s = c[l + " " + o] || c["* " + o], !s) for (r in c) if (a = r.split(" "), a[1] === o && (s = c[l + " " + a[0]] || c["* " + a[0]])) {
                s === !0 ? s = c[r] : c[r] !== !0 && (o = a[0], u.unshift(a[1]));
                break
            }
            if (s !== !0) if (s && e["throws"]) t = s(t);
            else try {
                t = s(t)
            } catch(f) {
                return {
                    state: "parsererror",
                    error: s ? f: "No conversion from " + l + " to " + o
                }
            }
        }
        return {
            state: "success",
            data: t
        }
    }
    function $(e, t, n, i) {
        var r;
        if (re.isArray(t)) re.each(t,
        function(t, r) {
            n || Yt.test(e) ? i(e, r) : $(e + "[" + ("object" == typeof r ? t: "") + "]", r, n, i)
        });
        else if (n || "object" !== re.type(t)) i(e, t);
        else for (r in t) $(e + "[" + r + "]", t[r], n, i)
    }
    function X() {
        try {
            return new e.XMLHttpRequest
        } catch(t) {}
    }
    function U() {
        try {
            return new e.ActiveXObject("Microsoft.XMLHTTP")
        } catch(t) {}
    }
    function Y(e) {
        return re.isWindow(e) ? e: 9 === e.nodeType ? e.defaultView || e.parentWindow: !1
    }
    var V = [],
    Q = V.slice,
    K = V.concat,
    G = V.push,
    J = V.indexOf,
    Z = {},
    ee = Z.toString,
    te = Z.hasOwnProperty,
    ne = {},
    ie = "1.11.1",
    re = function(e, t) {
        return new re.fn.init(e, t)
    },
    oe = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,
    se = /^-ms-/,
    ae = /-([\da-z])/gi,
    le = function(e, t) {
        return t.toUpperCase()
    };
    re.fn = re.prototype = {
        jquery: ie,
        constructor: re,
        selector: "",
        length: 0,
        toArray: function() {
            return Q.call(this)
        },
        get: function(e) {
            return null != e ? 0 > e ? this[e + this.length] : this[e] : Q.call(this)
        },
        pushStack: function(e) {
            var t = re.merge(this.constructor(), e);
            return t.prevObject = this,
            t.context = this.context,
            t
        },
        each: function(e, t) {
            return re.each(this, e, t)
        },
        map: function(e) {
            return this.pushStack(re.map(this,
            function(t, n) {
                return e.call(t, n, t)
            }))
        },
        slice: function() {
            return this.pushStack(Q.apply(this, arguments))
        },
        first: function() {
            return this.eq(0)
        },
        last: function() {
            return this.eq( - 1)
        },
        eq: function(e) {
            var t = this.length,
            n = +e + (0 > e ? t: 0);
            return this.pushStack(n >= 0 && t > n ? [this[n]] : [])
        },
        end: function() {
            return this.prevObject || this.constructor(null)
        },
        push: G,
        sort: V.sort,
        splice: V.splice
    },
    re.extend = re.fn.extend = function() {
        var e, t, n, i, r, o, s = arguments[0] || {},
        a = 1,
        l = arguments.length,
        c = !1;
        for ("boolean" == typeof s && (c = s, s = arguments[a] || {},
        a++), "object" == typeof s || re.isFunction(s) || (s = {}), a === l && (s = this, a--); l > a; a++) if (null != (r = arguments[a])) for (i in r) e = s[i],
        n = r[i],
        s !== n && (c && n && (re.isPlainObject(n) || (t = re.isArray(n))) ? (t ? (t = !1, o = e && re.isArray(e) ? e: []) : o = e && re.isPlainObject(e) ? e: {},
        s[i] = re.extend(c, o, n)) : void 0 !== n && (s[i] = n));
        return s
    },
    re.extend({
        expando: "jQuery" + (ie + Math.random()).replace(/\D/g, ""),
        isReady: !0,
        error: function(e) {
            throw new Error(e)
        },
        noop: function() {},
        isFunction: function(e) {
            return "function" === re.type(e)
        },
        isArray: Array.isArray ||
        function(e) {
            return "array" === re.type(e)
        },
        isWindow: function(e) {
            return null != e && e == e.window
        },
        isNumeric: function(e) {
            return ! re.isArray(e) && e - parseFloat(e) >= 0
        },
        isEmptyObject: function(e) {
            var t;
            for (t in e) return ! 1;
            return ! 0
        },
        isPlainObject: function(e) {
            var t;
            if (!e || "object" !== re.type(e) || e.nodeType || re.isWindow(e)) return ! 1;
            try {
                if (e.constructor && !te.call(e, "constructor") && !te.call(e.constructor.prototype, "isPrototypeOf")) return ! 1
            } catch(n) {
                return ! 1
            }
            if (ne.ownLast) for (t in e) return te.call(e, t);
            for (t in e);
            return void 0 === t || te.call(e, t)
        },
        type: function(e) {
            return null == e ? e + "": "object" == typeof e || "function" == typeof e ? Z[ee.call(e)] || "object": typeof e
        },
        globalEval: function(t) {
            t && re.trim(t) && (e.execScript ||
            function(t) {
                e.eval.call(e, t)
            })(t)
        },
        camelCase: function(e) {
            return e.replace(se, "ms-").replace(ae, le)
        },
        nodeName: function(e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        },
        each: function(e, t, i) {
            var r, o = 0,
            s = e.length,
            a = n(e);
            if (i) {
                if (a) for (; s > o && (r = t.apply(e[o], i), r !== !1); o++);
                else for (o in e) if (r = t.apply(e[o], i), r === !1) break
            } else if (a) for (; s > o && (r = t.call(e[o], o, e[o]), r !== !1); o++);
            else for (o in e) if (r = t.call(e[o], o, e[o]), r === !1) break;
            return e
        },
        trim: function(e) {
            return null == e ? "": (e + "").replace(oe, "")
        },
        makeArray: function(e, t) {
            var i = t || [];
            return null != e && (n(Object(e)) ? re.merge(i, "string" == typeof e ? [e] : e) : G.call(i, e)),
            i
        },
        inArray: function(e, t, n) {
            var i;
            if (t) {
                if (J) return J.call(t, e, n);
                for (i = t.length, n = n ? 0 > n ? Math.max(0, i + n) : n: 0; i > n; n++) if (n in t && t[n] === e) return n
            }
            return - 1
        },
        merge: function(e, t) {
            for (var n = +t.length,
            i = 0,
            r = e.length; n > i;) e[r++] = t[i++];
            if (n !== n) for (; void 0 !== t[i];) e[r++] = t[i++];
            return e.length = r,
            e
        },
        grep: function(e, t, n) {
            for (var i, r = [], o = 0, s = e.length, a = !n; s > o; o++) i = !t(e[o], o),
            i !== a && r.push(e[o]);
            return r
        },
        map: function(e, t, i) {
            var r, o = 0,
            s = e.length,
            a = n(e),
            l = [];
            if (a) for (; s > o; o++) r = t(e[o], o, i),
            null != r && l.push(r);
            else for (o in e) r = t(e[o], o, i),
            null != r && l.push(r);
            return K.apply([], l)
        },
        guid: 1,
        proxy: function(e, t) {
            var n, i, r;
            return "string" == typeof t && (r = e[t], t = e, e = r),
            re.isFunction(e) ? (n = Q.call(arguments, 2), i = function() {
                return e.apply(t || this, n.concat(Q.call(arguments)))
            },
            i.guid = e.guid = e.guid || re.guid++, i) : void 0
        },
        now: function() {
            return + new Date
        },
        support: ne
    }),
    re.each("Boolean Number String Function Array Date RegExp Object Error".split(" "),
    function(e, t) {
        Z["[object " + t + "]"] = t.toLowerCase()
    });
    var ce = function(e) {
        function t(e, t, n, i) {
            var r, o, s, a, l, c, f, d, h, g;
            if ((t ? t.ownerDocument || t: q) !== j && A(t), t = t || j, n = n || [], !e || "string" != typeof e) return n;
            if (1 !== (a = t.nodeType) && 9 !== a) return [];
            if (H && !i) {
                if (r = ye.exec(e)) if (s = r[1]) {
                    if (9 === a) {
                        if (o = t.getElementById(s), !o || !o.parentNode) return n;
                        if (o.id === s) return n.push(o),
                        n
                    } else if (t.ownerDocument && (o = t.ownerDocument.getElementById(s)) && I(t, o) && o.id === s) return n.push(o),
                    n
                } else {
                    if (r[2]) return Z.apply(n, t.getElementsByTagName(e)),
                    n;
                    if ((s = r[3]) && w.getElementsByClassName && t.getElementsByClassName) return Z.apply(n, t.getElementsByClassName(s)),
                    n
                }
                if (w.qsa && (!M || !M.test(e))) {
                    if (d = f = F, h = t, g = 9 === a && e, 1 === a && "object" !== t.nodeName.toLowerCase()) {
                        for (c = N(e), (f = t.getAttribute("id")) ? d = f.replace(xe, "\\$&") : t.setAttribute("id", d), d = "[id='" + d + "'] ", l = c.length; l--;) c[l] = d + p(c[l]);
                        h = be.test(e) && u(t.parentNode) || t,
                        g = c.join(",")
                    }
                    if (g) try {
                        return Z.apply(n, h.querySelectorAll(g)),
                        n
                    } catch(m) {} finally {
                        f || t.removeAttribute("id")
                    }
                }
            }
            return k(e.replace(le, "$1"), t, n, i)
        }
        function n() {
            function e(n, i) {
                return t.push(n + " ") > T.cacheLength && delete e[t.shift()],
                e[n + " "] = i
            }
            var t = [];
            return e
        }
        function i(e) {
            return e[F] = !0,
            e
        }
        function r(e) {
            var t = j.createElement("div");
            try {
                return !! e(t)
            } catch(n) {
                return ! 1
            } finally {
                t.parentNode && t.parentNode.removeChild(t),
                t = null
            }
        }
        function o(e, t) {
            for (var n = e.split("|"), i = e.length; i--;) T.attrHandle[n[i]] = t
        }
        function s(e, t) {
            var n = t && e,
            i = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || V) - (~e.sourceIndex || V);
            if (i) return i;
            if (n) for (; n = n.nextSibling;) if (n === t) return - 1;
            return e ? 1 : -1
        }
        function a(e) {
            return function(t) {
                var n = t.nodeName.toLowerCase();
                return "input" === n && t.type === e
            }
        }
        function l(e) {
            return function(t) {
                var n = t.nodeName.toLowerCase();
                return ("input" === n || "button" === n) && t.type === e
            }
        }
        function c(e) {
            return i(function(t) {
                return t = +t,
                i(function(n, i) {
                    for (var r, o = e([], n.length, t), s = o.length; s--;) n[r = o[s]] && (n[r] = !(i[r] = n[r]))
                })
            })
        }
        function u(e) {
            return e && typeof e.getElementsByTagName !== Y && e
        }
        function f() {}
        function p(e) {
            for (var t = 0,
            n = e.length,
            i = ""; n > t; t++) i += e[t].value;
            return i
        }
        function d(e, t, n) {
            var i = t.dir,
            r = n && "parentNode" === i,
            o = R++;
            return t.first ?
            function(t, n, o) {
                for (; t = t[i];) if (1 === t.nodeType || r) return e(t, n, o)
            }: function(t, n, s) {
                var a, l, c = [B, o];
                if (s) {
                    for (; t = t[i];) if ((1 === t.nodeType || r) && e(t, n, s)) return ! 0
                } else for (; t = t[i];) if (1 === t.nodeType || r) {
                    if (l = t[F] || (t[F] = {}), (a = l[i]) && a[0] === B && a[1] === o) return c[2] = a[2];
                    if (l[i] = c, c[2] = e(t, n, s)) return ! 0
                }
            }
        }
        function h(e) {
            return e.length > 1 ?
            function(t, n, i) {
                for (var r = e.length; r--;) if (!e[r](t, n, i)) return ! 1;
                return ! 0
            }: e[0]
        }
        function g(e, n, i) {
            for (var r = 0,
            o = n.length; o > r; r++) t(e, n[r], i);
            return i
        }
        function m(e, t, n, i, r) {
            for (var o, s = [], a = 0, l = e.length, c = null != t; l > a; a++)(o = e[a]) && (!n || n(o, i, r)) && (s.push(o), c && t.push(a));
            return s
        }
        function v(e, t, n, r, o, s) {
            return r && !r[F] && (r = v(r)),
            o && !o[F] && (o = v(o, s)),
            i(function(i, s, a, l) {
                var c, u, f, p = [],
                d = [],
                h = s.length,
                v = i || g(t || "*", a.nodeType ? [a] : a, []),
                y = !e || !i && t ? v: m(v, p, e, a, l),
                b = n ? o || (i ? e: h || r) ? [] : s: y;
                if (n && n(y, b, a, l), r) for (c = m(b, d), r(c, [], a, l), u = c.length; u--;)(f = c[u]) && (b[d[u]] = !(y[d[u]] = f));
                if (i) {
                    if (o || e) {
                        if (o) {
                            for (c = [], u = b.length; u--;)(f = b[u]) && c.push(y[u] = f);
                            o(null, b = [], c, l)
                        }
                        for (u = b.length; u--;)(f = b[u]) && (c = o ? te.call(i, f) : p[u]) > -1 && (i[c] = !(s[c] = f))
                    }
                } else b = m(b === s ? b.splice(h, b.length) : b),
                o ? o(null, s, b, l) : Z.apply(s, b)
            })
        }
        function y(e) {
            for (var t, n, i, r = e.length,
            o = T.relative[e[0].type], s = o || T.relative[" "], a = o ? 1 : 0, l = d(function(e) {
                return e === t
            },
            s, !0), c = d(function(e) {
                return te.call(t, e) > -1
            },
            s, !0), u = [function(e, n, i) {
                return ! o && (i || n !== S) || ((t = n).nodeType ? l(e, n, i) : c(e, n, i))
            }]; r > a; a++) if (n = T.relative[e[a].type]) u = [d(h(u), n)];
            else {
                if (n = T.filter[e[a].type].apply(null, e[a].matches), n[F]) {
                    for (i = ++a; r > i && !T.relative[e[i].type]; i++);
                    return v(a > 1 && h(u), a > 1 && p(e.slice(0, a - 1).concat({
                        value: " " === e[a - 2].type ? "*": ""
                    })).replace(le, "$1"), n, i > a && y(e.slice(a, i)), r > i && y(e = e.slice(i)), r > i && p(e))
                }
                u.push(n)
            }
            return h(u)
        }
        function b(e, n) {
            var r = n.length > 0,
            o = e.length > 0,
            s = function(i, s, a, l, c) {
                var u, f, p, d = 0,
                h = "0",
                g = i && [],
                v = [],
                y = S,
                b = i || o && T.find.TAG("*", c),
                x = B += null == y ? 1 : Math.random() || .1,
                w = b.length;
                for (c && (S = s !== j && s); h !== w && null != (u = b[h]); h++) {
                    if (o && u) {
                        for (f = 0; p = e[f++];) if (p(u, s, a)) {
                            l.push(u);
                            break
                        }
                        c && (B = x)
                    }
                    r && ((u = !p && u) && d--, i && g.push(u))
                }
                if (d += h, r && h !== d) {
                    for (f = 0; p = n[f++];) p(g, v, s, a);
                    if (i) {
                        if (d > 0) for (; h--;) g[h] || v[h] || (v[h] = G.call(l));
                        v = m(v)
                    }
                    Z.apply(l, v),
                    c && !i && v.length > 0 && d + n.length > 1 && t.uniqueSort(l)
                }
                return c && (B = x, S = y),
                g
            };
            return r ? i(s) : s
        }
        var x, w, T, _, C, N, E, k, S, D, P, A, j, L, H, M, O, W, I, F = "sizzle" + -new Date,
        q = e.document,
        B = 0,
        R = 0,
        z = n(),
        $ = n(),
        X = n(),
        U = function(e, t) {
            return e === t && (P = !0),
            0
        },
        Y = "undefined",
        V = 1 << 31,
        Q = {}.hasOwnProperty,
        K = [],
        G = K.pop,
        J = K.push,
        Z = K.push,
        ee = K.slice,
        te = K.indexOf ||
        function(e) {
            for (var t = 0,
            n = this.length; n > t; t++) if (this[t] === e) return t;
            return - 1
        },
        ne = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
        ie = "[\\x20\\t\\r\\n\\f]",
        re = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",
        oe = re.replace("w", "w#"),
        se = "\\[" + ie + "*(" + re + ")(?:" + ie + "*([*^$|!~]?=)" + ie + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + oe + "))|)" + ie + "*\\]",
        ae = ":(" + re + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + se + ")*)|.*)\\)|)",
        le = new RegExp("^" + ie + "+|((?:^|[^\\\\])(?:\\\\.)*)" + ie + "+$", "g"),
        ce = new RegExp("^" + ie + "*," + ie + "*"),
        ue = new RegExp("^" + ie + "*([>+~]|" + ie + ")" + ie + "*"),
        fe = new RegExp("=" + ie + "*([^\\]'\"]*?)" + ie + "*\\]", "g"),
        pe = new RegExp(ae),
        de = new RegExp("^" + oe + "$"),
        he = {
            ID: new RegExp("^#(" + re + ")"),
            CLASS: new RegExp("^\\.(" + re + ")"),
            TAG: new RegExp("^(" + re.replace("w", "w*") + ")"),
            ATTR: new RegExp("^" + se),
            PSEUDO: new RegExp("^" + ae),
            CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + ie + "*(even|odd|(([+-]|)(\\d*)n|)" + ie + "*(?:([+-]|)" + ie + "*(\\d+)|))" + ie + "*\\)|)", "i"),
            bool: new RegExp("^(?:" + ne + ")$", "i"),
            needsContext: new RegExp("^" + ie + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + ie + "*((?:-\\d)?\\d*)" + ie + "*\\)|)(?=[^-]|$)", "i")
        },
        ge = /^(?:input|select|textarea|button)$/i,
        me = /^h\d$/i,
        ve = /^[^{]+\{\s*\[native \w/,
        ye = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,
        be = /[+~]/,
        xe = /'|\\/g,
        we = new RegExp("\\\\([\\da-f]{1,6}" + ie + "?|(" + ie + ")|.)", "ig"),
        Te = function(e, t, n) {
            var i = "0x" + t - 65536;
            return i !== i || n ? t: 0 > i ? String.fromCharCode(i + 65536) : String.fromCharCode(i >> 10 | 55296, 1023 & i | 56320)
        };
        try {
            Z.apply(K = ee.call(q.childNodes), q.childNodes),
            K[q.childNodes.length].nodeType
        } catch(_e) {
            Z = {
                apply: K.length ?
                function(e, t) {
                    J.apply(e, ee.call(t))
                }: function(e, t) {
                    for (var n = e.length,
                    i = 0; e[n++] = t[i++];);
                    e.length = n - 1
                }
            }
        }
        w = t.support = {},
        C = t.isXML = function(e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return t ? "HTML" !== t.nodeName: !1
        },
        A = t.setDocument = function(e) {
            var t, n = e ? e.ownerDocument || e: q,
            i = n.defaultView;
            return n !== j && 9 === n.nodeType && n.documentElement ? (j = n, L = n.documentElement, H = !C(n), i && i !== i.top && (i.addEventListener ? i.addEventListener("unload",
            function() {
                A()
            },
            !1) : i.attachEvent && i.attachEvent("onunload",
            function() {
                A()
            })), w.attributes = r(function(e) {
                return e.className = "i",
                !e.getAttribute("className")
            }), w.getElementsByTagName = r(function(e) {
                return e.appendChild(n.createComment("")),
                !e.getElementsByTagName("*").length
            }), w.getElementsByClassName = ve.test(n.getElementsByClassName) && r(function(e) {
                return e.innerHTML = "<div class='a'></div><div class='a i'></div>",
                e.firstChild.className = "i",
                2 === e.getElementsByClassName("i").length
            }), w.getById = r(function(e) {
                return L.appendChild(e).id = F,
                !n.getElementsByName || !n.getElementsByName(F).length
            }), w.getById ? (T.find.ID = function(e, t) {
                if (typeof t.getElementById !== Y && H) {
                    var n = t.getElementById(e);
                    return n && n.parentNode ? [n] : []
                }
            },
            T.filter.ID = function(e) {
                var t = e.replace(we, Te);
                return function(e) {
                    return e.getAttribute("id") === t
                }
            }) : (delete T.find.ID, T.filter.ID = function(e) {
                var t = e.replace(we, Te);
                return function(e) {
                    var n = typeof e.getAttributeNode !== Y && e.getAttributeNode("id");
                    return n && n.value === t
                }
            }), T.find.TAG = w.getElementsByTagName ?
            function(e, t) {
                return typeof t.getElementsByTagName !== Y ? t.getElementsByTagName(e) : void 0
            }: function(e, t) {
                var n, i = [],
                r = 0,
                o = t.getElementsByTagName(e);
                if ("*" === e) {
                    for (; n = o[r++];) 1 === n.nodeType && i.push(n);
                    return i
                }
                return o
            },
            T.find.CLASS = w.getElementsByClassName &&
            function(e, t) {
                return typeof t.getElementsByClassName !== Y && H ? t.getElementsByClassName(e) : void 0
            },
            O = [], M = [], (w.qsa = ve.test(n.querySelectorAll)) && (r(function(e) {
                e.innerHTML = "<select msallowclip=''><option selected=''></option></select>",
                e.querySelectorAll("[msallowclip^='']").length && M.push("[*^$]=" + ie + "*(?:''|\"\")"),
                e.querySelectorAll("[selected]").length || M.push("\\[" + ie + "*(?:value|" + ne + ")"),
                e.querySelectorAll(":checked").length || M.push(":checked")
            }), r(function(e) {
                var t = n.createElement("input");
                t.setAttribute("type", "hidden"),
                e.appendChild(t).setAttribute("name", "D"),
                e.querySelectorAll("[name=d]").length && M.push("name" + ie + "*[*^$|!~]?="),
                e.querySelectorAll(":enabled").length || M.push(":enabled", ":disabled"),
                e.querySelectorAll("*,:x"),
                M.push(",.*:")
            })), (w.matchesSelector = ve.test(W = L.matches || L.webkitMatchesSelector || L.mozMatchesSelector || L.oMatchesSelector || L.msMatchesSelector)) && r(function(e) {
                w.disconnectedMatch = W.call(e, "div"),
                W.call(e, "[s!='']:x"),
                O.push("!=", ae)
            }), M = M.length && new RegExp(M.join("|")), O = O.length && new RegExp(O.join("|")), t = ve.test(L.compareDocumentPosition), I = t || ve.test(L.contains) ?
            function(e, t) {
                var n = 9 === e.nodeType ? e.documentElement: e,
                i = t && t.parentNode;
                return e === i || !(!i || 1 !== i.nodeType || !(n.contains ? n.contains(i) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(i)))
            }: function(e, t) {
                if (t) for (; t = t.parentNode;) if (t === e) return ! 0;
                return ! 1
            },
            U = t ?
            function(e, t) {
                if (e === t) return P = !0,
                0;
                var i = !e.compareDocumentPosition - !t.compareDocumentPosition;
                return i ? i: (i = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1, 1 & i || !w.sortDetached && t.compareDocumentPosition(e) === i ? e === n || e.ownerDocument === q && I(q, e) ? -1 : t === n || t.ownerDocument === q && I(q, t) ? 1 : D ? te.call(D, e) - te.call(D, t) : 0 : 4 & i ? -1 : 1)
            }: function(e, t) {
                if (e === t) return P = !0,
                0;
                var i, r = 0,
                o = e.parentNode,
                a = t.parentNode,
                l = [e],
                c = [t];
                if (!o || !a) return e === n ? -1 : t === n ? 1 : o ? -1 : a ? 1 : D ? te.call(D, e) - te.call(D, t) : 0;
                if (o === a) return s(e, t);
                for (i = e; i = i.parentNode;) l.unshift(i);
                for (i = t; i = i.parentNode;) c.unshift(i);
                for (; l[r] === c[r];) r++;
                return r ? s(l[r], c[r]) : l[r] === q ? -1 : c[r] === q ? 1 : 0
            },
            n) : j
        },
        t.matches = function(e, n) {
            return t(e, null, null, n)
        },
        t.matchesSelector = function(e, n) {
            if ((e.ownerDocument || e) !== j && A(e), n = n.replace(fe, "='$1']"), !(!w.matchesSelector || !H || O && O.test(n) || M && M.test(n))) try {
                var i = W.call(e, n);
                if (i || w.disconnectedMatch || e.document && 11 !== e.document.nodeType) return i
            } catch(r) {}
            return t(n, j, null, [e]).length > 0
        },
        t.contains = function(e, t) {
            return (e.ownerDocument || e) !== j && A(e),
            I(e, t)
        },
        t.attr = function(e, t) { (e.ownerDocument || e) !== j && A(e);
            var n = T.attrHandle[t.toLowerCase()],
            i = n && Q.call(T.attrHandle, t.toLowerCase()) ? n(e, t, !H) : void 0;
            return void 0 !== i ? i: w.attributes || !H ? e.getAttribute(t) : (i = e.getAttributeNode(t)) && i.specified ? i.value: null
        },
        t.error = function(e) {
            throw new Error("Syntax error, unrecognized expression: " + e)
        },
        t.uniqueSort = function(e) {
            var t, n = [],
            i = 0,
            r = 0;
            if (P = !w.detectDuplicates, D = !w.sortStable && e.slice(0), e.sort(U), P) {
                for (; t = e[r++];) t === e[r] && (i = n.push(r));
                for (; i--;) e.splice(n[i], 1)
            }
            return D = null,
            e
        },
        _ = t.getText = function(e) {
            var t, n = "",
            i = 0,
            r = e.nodeType;
            if (r) {
                if (1 === r || 9 === r || 11 === r) {
                    if ("string" == typeof e.textContent) return e.textContent;
                    for (e = e.firstChild; e; e = e.nextSibling) n += _(e)
                } else if (3 === r || 4 === r) return e.nodeValue
            } else for (; t = e[i++];) n += _(t);
            return n
        },
        T = t.selectors = {
            cacheLength: 50,
            createPseudo: i,
            match: he,
            attrHandle: {},
            find: {},
            relative: {
                ">": {
                    dir: "parentNode",
                    first: !0
                },
                " ": {
                    dir: "parentNode"
                },
                "+": {
                    dir: "previousSibling",
                    first: !0
                },
                "~": {
                    dir: "previousSibling"
                }
            },
            preFilter: {
                ATTR: function(e) {
                    return e[1] = e[1].replace(we, Te),
                    e[3] = (e[3] || e[4] || e[5] || "").replace(we, Te),
                    "~=" === e[2] && (e[3] = " " + e[3] + " "),
                    e.slice(0, 4)
                },
                CHILD: function(e) {
                    return e[1] = e[1].toLowerCase(),
                    "nth" === e[1].slice(0, 3) ? (e[3] || t.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && t.error(e[0]),
                    e
                },
                PSEUDO: function(e) {
                    var t, n = !e[6] && e[2];
                    return he.CHILD.test(e[0]) ? null: (e[3] ? e[2] = e[4] || e[5] || "": n && pe.test(n) && (t = N(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                }
            },
            filter: {
                TAG: function(e) {
                    var t = e.replace(we, Te).toLowerCase();
                    return "*" === e ?
                    function() {
                        return ! 0
                    }: function(e) {
                        return e.nodeName && e.nodeName.toLowerCase() === t
                    }
                },
                CLASS: function(e) {
                    var t = z[e + " "];
                    return t || (t = new RegExp("(^|" + ie + ")" + e + "(" + ie + "|$)")) && z(e,
                    function(e) {
                        return t.test("string" == typeof e.className && e.className || typeof e.getAttribute !== Y && e.getAttribute("class") || "")
                    })
                },
                ATTR: function(e, n, i) {
                    return function(r) {
                        var o = t.attr(r, e);
                        return null == o ? "!=" === n: n ? (o += "", "=" === n ? o === i: "!=" === n ? o !== i: "^=" === n ? i && 0 === o.indexOf(i) : "*=" === n ? i && o.indexOf(i) > -1 : "$=" === n ? i && o.slice( - i.length) === i: "~=" === n ? (" " + o + " ").indexOf(i) > -1 : "|=" === n ? o === i || o.slice(0, i.length + 1) === i + "-": !1) : !0
                    }
                },
                CHILD: function(e, t, n, i, r) {
                    var o = "nth" !== e.slice(0, 3),
                    s = "last" !== e.slice( - 4),
                    a = "of-type" === t;
                    return 1 === i && 0 === r ?
                    function(e) {
                        return !! e.parentNode
                    }: function(t, n, l) {
                        var c, u, f, p, d, h, g = o !== s ? "nextSibling": "previousSibling",
                        m = t.parentNode,
                        v = a && t.nodeName.toLowerCase(),
                        y = !l && !a;
                        if (m) {
                            if (o) {
                                for (; g;) {
                                    for (f = t; f = f[g];) if (a ? f.nodeName.toLowerCase() === v: 1 === f.nodeType) return ! 1;
                                    h = g = "only" === e && !h && "nextSibling"
                                }
                                return ! 0
                            }
                            if (h = [s ? m.firstChild: m.lastChild], s && y) {
                                for (u = m[F] || (m[F] = {}), c = u[e] || [], d = c[0] === B && c[1], p = c[0] === B && c[2], f = d && m.childNodes[d]; f = ++d && f && f[g] || (p = d = 0) || h.pop();) if (1 === f.nodeType && ++p && f === t) {
                                    u[e] = [B, d, p];
                                    break
                                }
                            } else if (y && (c = (t[F] || (t[F] = {}))[e]) && c[0] === B) p = c[1];
                            else for (; (f = ++d && f && f[g] || (p = d = 0) || h.pop()) && ((a ? f.nodeName.toLowerCase() !== v: 1 !== f.nodeType) || !++p || (y && ((f[F] || (f[F] = {}))[e] = [B, p]), f !== t)););
                            return p -= r,
                            p === i || p % i === 0 && p / i >= 0
                        }
                    }
                },
                PSEUDO: function(e, n) {
                    var r, o = T.pseudos[e] || T.setFilters[e.toLowerCase()] || t.error("unsupported pseudo: " + e);
                    return o[F] ? o(n) : o.length > 1 ? (r = [e, e, "", n], T.setFilters.hasOwnProperty(e.toLowerCase()) ? i(function(e, t) {
                        for (var i, r = o(e, n), s = r.length; s--;) i = te.call(e, r[s]),
                        e[i] = !(t[i] = r[s])
                    }) : function(e) {
                        return o(e, 0, r)
                    }) : o
                }
            },
            pseudos: {
                not: i(function(e) {
                    var t = [],
                    n = [],
                    r = E(e.replace(le, "$1"));
                    return r[F] ? i(function(e, t, n, i) {
                        for (var o, s = r(e, null, i, []), a = e.length; a--;)(o = s[a]) && (e[a] = !(t[a] = o))
                    }) : function(e, i, o) {
                        return t[0] = e,
                        r(t, null, o, n),
                        !n.pop()
                    }
                }),
                has: i(function(e) {
                    return function(n) {
                        return t(e, n).length > 0
                    }
                }),
                contains: i(function(e) {
                    return function(t) {
                        return (t.textContent || t.innerText || _(t)).indexOf(e) > -1
                    }
                }),
                lang: i(function(e) {
                    return de.test(e || "") || t.error("unsupported lang: " + e),
                    e = e.replace(we, Te).toLowerCase(),
                    function(t) {
                        var n;
                        do
                        if (n = H ? t.lang: t.getAttribute("xml:lang") || t.getAttribute("lang")) return n = n.toLowerCase(),
                        n === e || 0 === n.indexOf(e + "-");
                        while ((t = t.parentNode) && 1 === t.nodeType);
                        return ! 1
                    }
                }),
                target: function(t) {

                    var n = e.location && e.location.hash;
                    return n && n.slice(1) === t.id
                },
                root: function(e) {
                    return e === L
                },
                focus: function(e) {
                    return e === j.activeElement && (!j.hasFocus || j.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                },
                enabled: function(e) {
                    return e.disabled === !1
                },
                disabled: function(e) {
                    return e.disabled === !0
                },
                checked: function(e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && !!e.checked || "option" === t && !!e.selected
                },
                selected: function(e) {
                    return e.parentNode && e.parentNode.selectedIndex,
                    e.selected === !0
                },
                empty: function(e) {
                    for (e = e.firstChild; e; e = e.nextSibling) if (e.nodeType < 6) return ! 1;
                    return ! 0
                },
                parent: function(e) {
                    return ! T.pseudos.empty(e)
                },
                header: function(e) {
                    return me.test(e.nodeName)
                },
                input: function(e) {
                    return ge.test(e.nodeName)
                },
                button: function(e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && "button" === e.type || "button" === t
                },
                text: function(e) {
                    var t;
                    return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
                },
                first: c(function() {
                    return [0]
                }),
                last: c(function(e, t) {
                    return [t - 1]
                }),
                eq: c(function(e, t, n) {
                    return [0 > n ? n + t: n]
                }),
                even: c(function(e, t) {
                    for (var n = 0; t > n; n += 2) e.push(n);
                    return e
                }),
                odd: c(function(e, t) {
                    for (var n = 1; t > n; n += 2) e.push(n);
                    return e
                }),
                lt: c(function(e, t, n) {
                    for (var i = 0 > n ? n + t: n; --i >= 0;) e.push(i);
                    return e
                }),
                gt: c(function(e, t, n) {
                    for (var i = 0 > n ? n + t: n; ++i < t;) e.push(i);
                    return e
                })
            }
        },
        T.pseudos.nth = T.pseudos.eq;
        for (x in {
            radio: !0,
            checkbox: !0,
            file: !0,
            password: !0,
            image: !0
        }) T.pseudos[x] = a(x);
        for (x in {
            submit: !0,
            reset: !0
        }) T.pseudos[x] = l(x);
        return f.prototype = T.filters = T.pseudos,
        T.setFilters = new f,
        N = t.tokenize = function(e, n) {
            var i, r, o, s, a, l, c, u = $[e + " "];
            if (u) return n ? 0 : u.slice(0);
            for (a = e, l = [], c = T.preFilter; a;) { (!i || (r = ce.exec(a))) && (r && (a = a.slice(r[0].length) || a), l.push(o = [])),
                i = !1,
                (r = ue.exec(a)) && (i = r.shift(), o.push({
                    value: i,
                    type: r[0].replace(le, " ")
                }), a = a.slice(i.length));
                for (s in T.filter) ! (r = he[s].exec(a)) || c[s] && !(r = c[s](r)) || (i = r.shift(), o.push({
                    value: i,
                    type: s,
                    matches: r
                }), a = a.slice(i.length));
                if (!i) break
            }
            return n ? a.length: a ? t.error(e) : $(e, l).slice(0)
        },
        E = t.compile = function(e, t) {
            var n, i = [],
            r = [],
            o = X[e + " "];
            if (!o) {
                for (t || (t = N(e)), n = t.length; n--;) o = y(t[n]),
                o[F] ? i.push(o) : r.push(o);
                o = X(e, b(r, i)),
                o.selector = e
            }
            return o
        },
        k = t.select = function(e, t, n, i) {
            var r, o, s, a, l, c = "function" == typeof e && e,
            f = !i && N(e = c.selector || e);
            if (n = n || [], 1 === f.length) {
                if (o = f[0] = f[0].slice(0), o.length > 2 && "ID" === (s = o[0]).type && w.getById && 9 === t.nodeType && H && T.relative[o[1].type]) {
                    if (t = (T.find.ID(s.matches[0].replace(we, Te), t) || [])[0], !t) return n;
                    c && (t = t.parentNode),
                    e = e.slice(o.shift().value.length)
                }
                for (r = he.needsContext.test(e) ? 0 : o.length; r--&&(s = o[r], !T.relative[a = s.type]);) if ((l = T.find[a]) && (i = l(s.matches[0].replace(we, Te), be.test(o[0].type) && u(t.parentNode) || t))) {
                    if (o.splice(r, 1), e = i.length && p(o), !e) return Z.apply(n, i),
                    n;
                    break
                }
            }
            return (c || E(e, f))(i, t, !H, n, be.test(e) && u(t.parentNode) || t),
            n
        },
        w.sortStable = F.split("").sort(U).join("") === F,
        w.detectDuplicates = !!P,
        A(),
        w.sortDetached = r(function(e) {
            return 1 & e.compareDocumentPosition(j.createElement("div"))
        }),
        r(function(e) {
            return e.innerHTML = "<a href='#'></a>",
            "#" === e.firstChild.getAttribute("href")
        }) || o("type|href|height|width",
        function(e, t, n) {
            return n ? void 0 : e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
        }),
        w.attributes && r(function(e) {
            return e.innerHTML = "<input/>",
            e.firstChild.setAttribute("value", ""),
            "" === e.firstChild.getAttribute("value")
        }) || o("value",
        function(e, t, n) {
            return n || "input" !== e.nodeName.toLowerCase() ? void 0 : e.defaultValue
        }),
        r(function(e) {
            return null == e.getAttribute("disabled")
        }) || o(ne,
        function(e, t, n) {
            var i;
            return n ? void 0 : e[t] === !0 ? t.toLowerCase() : (i = e.getAttributeNode(t)) && i.specified ? i.value: null
        }),
        t
    } (e);
    re.find = ce,
    re.expr = ce.selectors,
    re.expr[":"] = re.expr.pseudos,
    re.unique = ce.uniqueSort,
    re.text = ce.getText,
    re.isXMLDoc = ce.isXML,
    re.contains = ce.contains;
    var ue = re.expr.match.needsContext,
    fe = /^<(\w+)\s*\/?>(?:<\/\1>|)$/,
    pe = /^.[^:#\[\.,]*$/;
    re.filter = function(e, t, n) {
        var i = t[0];
        return n && (e = ":not(" + e + ")"),
        1 === t.length && 1 === i.nodeType ? re.find.matchesSelector(i, e) ? [i] : [] : re.find.matches(e, re.grep(t,
        function(e) {
            return 1 === e.nodeType
        }))
    },
    re.fn.extend({
        find: function(e) {
            var t, n = [],
            i = this,
            r = i.length;
            if ("string" != typeof e) return this.pushStack(re(e).filter(function() {
                for (t = 0; r > t; t++) if (re.contains(i[t], this)) return ! 0
            }));
            for (t = 0; r > t; t++) re.find(e, i[t], n);
            return n = this.pushStack(r > 1 ? re.unique(n) : n),
            n.selector = this.selector ? this.selector + " " + e: e,
            n
        },
        filter: function(e) {
            return this.pushStack(i(this, e || [], !1))
        },
        not: function(e) {
            return this.pushStack(i(this, e || [], !0))
        },
        is: function(e) {
            return !! i(this, "string" == typeof e && ue.test(e) ? re(e) : e || [], !1).length
        }
    });
    var de, he = e.document,
    ge = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/,
    me = re.fn.init = function(e, t) {
        var n, i;
        if (!e) return this;
        if ("string" == typeof e) {
            if (n = "<" === e.charAt(0) && ">" === e.charAt(e.length - 1) && e.length >= 3 ? [null, e, null] : ge.exec(e), !n || !n[1] && t) return ! t || t.jquery ? (t || de).find(e) : this.constructor(t).find(e);
            if (n[1]) {
                if (t = t instanceof re ? t[0] : t, re.merge(this, re.parseHTML(n[1], t && t.nodeType ? t.ownerDocument || t: he, !0)), fe.test(n[1]) && re.isPlainObject(t)) for (n in t) re.isFunction(this[n]) ? this[n](t[n]) : this.attr(n, t[n]);
                return this
            }
            if (i = he.getElementById(n[2]), i && i.parentNode) {
                if (i.id !== n[2]) return de.find(e);
                this.length = 1,
                this[0] = i
            }
            return this.context = he,
            this.selector = e,
            this
        }
        return e.nodeType ? (this.context = this[0] = e, this.length = 1, this) : re.isFunction(e) ? "undefined" != typeof de.ready ? de.ready(e) : e(re) : (void 0 !== e.selector && (this.selector = e.selector, this.context = e.context), re.makeArray(e, this))
    };
    me.prototype = re.fn,
    de = re(he);
    var ve = /^(?:parents|prev(?:Until|All))/,
    ye = {
        children: !0,
        contents: !0,
        next: !0,
        prev: !0
    };
    re.extend({
        dir: function(e, t, n) {
            for (var i = [], r = e[t]; r && 9 !== r.nodeType && (void 0 === n || 1 !== r.nodeType || !re(r).is(n));) 1 === r.nodeType && i.push(r),
            r = r[t];
            return i
        },
        sibling: function(e, t) {
            for (var n = []; e; e = e.nextSibling) 1 === e.nodeType && e !== t && n.push(e);
            return n
        }
    }),
    re.fn.extend({
        has: function(e) {
            var t, n = re(e, this),
            i = n.length;
            return this.filter(function() {
                for (t = 0; i > t; t++) if (re.contains(this, n[t])) return ! 0
            })
        },
        closest: function(e, t) {
            for (var n, i = 0,
            r = this.length,
            o = [], s = ue.test(e) || "string" != typeof e ? re(e, t || this.context) : 0; r > i; i++) for (n = this[i]; n && n !== t; n = n.parentNode) if (n.nodeType < 11 && (s ? s.index(n) > -1 : 1 === n.nodeType && re.find.matchesSelector(n, e))) {
                o.push(n);
                break
            }
            return this.pushStack(o.length > 1 ? re.unique(o) : o)
        },
        index: function(e) {
            return e ? "string" == typeof e ? re.inArray(this[0], re(e)) : re.inArray(e.jquery ? e[0] : e, this) : this[0] && this[0].parentNode ? this.first().prevAll().length: -1
        },
        add: function(e, t) {
            return this.pushStack(re.unique(re.merge(this.get(), re(e, t))))
        },
        addBack: function(e) {
            return this.add(null == e ? this.prevObject: this.prevObject.filter(e))
        }
    }),
    re.each({
        parent: function(e) {
            var t = e.parentNode;
            return t && 11 !== t.nodeType ? t: null
        },
        parents: function(e) {
            return re.dir(e, "parentNode")
        },
        parentsUntil: function(e, t, n) {
            return re.dir(e, "parentNode", n)
        },
        next: function(e) {
            return r(e, "nextSibling")
        },
        prev: function(e) {
            return r(e, "previousSibling")
        },
        nextAll: function(e) {
            return re.dir(e, "nextSibling")
        },
        prevAll: function(e) {
            return re.dir(e, "previousSibling")
        },
        nextUntil: function(e, t, n) {
            return re.dir(e, "nextSibling", n)
        },
        prevUntil: function(e, t, n) {
            return re.dir(e, "previousSibling", n)
        },
        siblings: function(e) {
            return re.sibling((e.parentNode || {}).firstChild, e)
        },
        children: function(e) {
            return re.sibling(e.firstChild)
        },
        contents: function(e) {
            return re.nodeName(e, "iframe") ? e.contentDocument || e.contentWindow.document: re.merge([], e.childNodes)
        }
    },
    function(e, t) {
        re.fn[e] = function(n, i) {
            var r = re.map(this, t, n);
            return "Until" !== e.slice( - 5) && (i = n),
            i && "string" == typeof i && (r = re.filter(i, r)),
            this.length > 1 && (ye[e] || (r = re.unique(r)), ve.test(e) && (r = r.reverse())),
            this.pushStack(r)
        }
    });
    var be = /\S+/g,
    xe = {};
    re.Callbacks = function(e) {
        e = "string" == typeof e ? xe[e] || o(e) : re.extend({},
        e);
        var t, n, i, r, s, a, l = [],
        c = !e.once && [],
        u = function(o) {
            for (n = e.memory && o, i = !0, s = a || 0, a = 0, r = l.length, t = !0; l && r > s; s++) if (l[s].apply(o[0], o[1]) === !1 && e.stopOnFalse) {
                n = !1;
                break
            }
            t = !1,
            l && (c ? c.length && u(c.shift()) : n ? l = [] : f.disable())
        },
        f = {
            add: function() {
                if (l) {
                    var i = l.length; !
                    function o(t) {
                        re.each(t,
                        function(t, n) {
                            var i = re.type(n);
                            "function" === i ? e.unique && f.has(n) || l.push(n) : n && n.length && "string" !== i && o(n)
                        })
                    } (arguments),
                    t ? r = l.length: n && (a = i, u(n))
                }
                return this
            },
            remove: function() {
                return l && re.each(arguments,
                function(e, n) {
                    for (var i; (i = re.inArray(n, l, i)) > -1;) l.splice(i, 1),
                    t && (r >= i && r--, s >= i && s--)
                }),
                this
            },
            has: function(e) {
                return e ? re.inArray(e, l) > -1 : !(!l || !l.length)
            },
            empty: function() {
                return l = [],
                r = 0,
                this
            },
            disable: function() {
                return l = c = n = void 0,
                this
            },
            disabled: function() {
                return ! l
            },
            lock: function() {
                return c = void 0,
                n || f.disable(),
                this
            },
            locked: function() {
                return ! c
            },
            fireWith: function(e, n) {
                return ! l || i && !c || (n = n || [], n = [e, n.slice ? n.slice() : n], t ? c.push(n) : u(n)),
                this
            },
            fire: function() {
                return f.fireWith(this, arguments),
                this
            },
            fired: function() {
                return !! i
            }
        };
        return f
    },
    re.extend({
        Deferred: function(e) {
            var t = [["resolve", "done", re.Callbacks("once memory"), "resolved"], ["reject", "fail", re.Callbacks("once memory"), "rejected"], ["notify", "progress", re.Callbacks("memory")]],
            n = "pending",
            i = {
                state: function() {
                    return n
                },
                always: function() {
                    return r.done(arguments).fail(arguments),
                    this
                },
                then: function() {
                    var e = arguments;
                    return re.Deferred(function(n) {
                        re.each(t,
                        function(t, o) {
                            var s = re.isFunction(e[t]) && e[t];
                            r[o[1]](function() {
                                var e = s && s.apply(this, arguments);
                                e && re.isFunction(e.promise) ? e.promise().done(n.resolve).fail(n.reject).progress(n.notify) : n[o[0] + "With"](this === i ? n.promise() : this, s ? [e] : arguments)
                            })
                        }),
                        e = null
                    }).promise()
                },
                promise: function(e) {
                    return null != e ? re.extend(e, i) : i
                }
            },
            r = {};
            return i.pipe = i.then,
            re.each(t,
            function(e, o) {
                var s = o[2],
                a = o[3];
                i[o[1]] = s.add,
                a && s.add(function() {
                    n = a
                },
                t[1 ^ e][2].disable, t[2][2].lock),
                r[o[0]] = function() {
                    return r[o[0] + "With"](this === r ? i: this, arguments),
                    this
                },
                r[o[0] + "With"] = s.fireWith
            }),
            i.promise(r),
            e && e.call(r, r),
            r
        },
        when: function(e) {
            var t, n, i, r = 0,
            o = Q.call(arguments),
            s = o.length,
            a = 1 !== s || e && re.isFunction(e.promise) ? s: 0,
            l = 1 === a ? e: re.Deferred(),
            c = function(e, n, i) {
                return function(r) {
                    n[e] = this,
                    i[e] = arguments.length > 1 ? Q.call(arguments) : r,
                    i === t ? l.notifyWith(n, i) : --a || l.resolveWith(n, i)
                }
            };
            if (s > 1) for (t = new Array(s), n = new Array(s), i = new Array(s); s > r; r++) o[r] && re.isFunction(o[r].promise) ? o[r].promise().done(c(r, i, o)).fail(l.reject).progress(c(r, n, t)) : --a;
            return a || l.resolveWith(i, o),
            l.promise()
        }
    });
    var we;
    re.fn.ready = function(e) {
        return re.ready.promise().done(e),
        this
    },
    re.extend({
        isReady: !1,
        readyWait: 1,
        holdReady: function(e) {
            e ? re.readyWait++:re.ready(!0)
        },
        ready: function(e) {
            if (e === !0 ? !--re.readyWait: !re.isReady) {
                if (!he.body) return setTimeout(re.ready);
                re.isReady = !0,
                e !== !0 && --re.readyWait > 0 || (we.resolveWith(he, [re]), re.fn.triggerHandler && (re(he).triggerHandler("ready"), re(he).off("ready")))
            }
        }
    }),
    re.ready.promise = function(t) {
        if (!we) if (we = re.Deferred(), "complete" === he.readyState) setTimeout(re.ready);
        else if (he.addEventListener) he.addEventListener("DOMContentLoaded", a, !1),
        e.addEventListener("load", a, !1);
        else {
            he.attachEvent("onreadystatechange", a),
            e.attachEvent("onload", a);
            var n = !1;
            try {
                n = null == e.frameElement && he.documentElement
            } catch(i) {}
            n && n.doScroll && !
            function r() {
                if (!re.isReady) {
                    try {
                        n.doScroll("left")
                    } catch(e) {
                        return setTimeout(r, 50)
                    }
                    s(),
                    re.ready()
                }
            } ()
        }
        return we.promise(t)
    };
    var Te, _e = "undefined";
    for (Te in re(ne)) break;
    ne.ownLast = "0" !== Te,
    ne.inlineBlockNeedsLayout = !1,
    re(function() {
        var e, t, n, i;
        n = he.getElementsByTagName("body")[0],
        n && n.style && (t = he.createElement("div"), i = he.createElement("div"), i.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(i).appendChild(t), typeof t.style.zoom !== _e && (t.style.cssText = "display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1", ne.inlineBlockNeedsLayout = e = 3 === t.offsetWidth, e && (n.style.zoom = 1)), n.removeChild(i))
    }),
    function() {
        var e = he.createElement("div");
        if (null == ne.deleteExpando) {
            ne.deleteExpando = !0;
            try {
                delete e.test
            } catch(t) {
                ne.deleteExpando = !1
            }
        }
        e = null
    } (),
    re.acceptData = function(e) {
        var t = re.noData[(e.nodeName + " ").toLowerCase()],
        n = +e.nodeType || 1;
        return 1 !== n && 9 !== n ? !1 : !t || t !== !0 && e.getAttribute("classid") === t
    };
    var Ce = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/,
    Ne = /([A-Z])/g;
    re.extend({
        cache: {},
        noData: {
            "applet ": !0,
            "embed ": !0,
            "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
        },
        hasData: function(e) {
            return e = e.nodeType ? re.cache[e[re.expando]] : e[re.expando],
            !!e && !c(e)
        },
        data: function(e, t, n) {
            return u(e, t, n)
        },
        removeData: function(e, t) {
            return f(e, t)
        },
        _data: function(e, t, n) {
            return u(e, t, n, !0)
        },
        _removeData: function(e, t) {
            return f(e, t, !0)
        }
    }),
    re.fn.extend({
        data: function(e, t) {
            var n, i, r, o = this[0],
            s = o && o.attributes;
            if (void 0 === e) {
                if (this.length && (r = re.data(o), 1 === o.nodeType && !re._data(o, "parsedAttrs"))) {
                    for (n = s.length; n--;) s[n] && (i = s[n].name, 0 === i.indexOf("data-") && (i = re.camelCase(i.slice(5)), l(o, i, r[i])));
                    re._data(o, "parsedAttrs", !0)
                }
                return r
            }
            return "object" == typeof e ? this.each(function() {
                re.data(this, e)
            }) : arguments.length > 1 ? this.each(function() {
                re.data(this, e, t)
            }) : o ? l(o, e, re.data(o, e)) : void 0
        },
        removeData: function(e) {
            return this.each(function() {
                re.removeData(this, e)
            })
        }
    }),
    re.extend({
        queue: function(e, t, n) {
            var i;
            return e ? (t = (t || "fx") + "queue", i = re._data(e, t), n && (!i || re.isArray(n) ? i = re._data(e, t, re.makeArray(n)) : i.push(n)), i || []) : void 0
        },
        dequeue: function(e, t) {
            t = t || "fx";
            var n = re.queue(e, t),
            i = n.length,
            r = n.shift(),
            o = re._queueHooks(e, t),
            s = function() {
                re.dequeue(e, t)
            };
            "inprogress" === r && (r = n.shift(), i--),
            r && ("fx" === t && n.unshift("inprogress"), delete o.stop, r.call(e, s, o)),
            !i && o && o.empty.fire()
        },
        _queueHooks: function(e, t) {
            var n = t + "queueHooks";
            return re._data(e, n) || re._data(e, n, {
                empty: re.Callbacks("once memory").add(function() {
                    re._removeData(e, t + "queue"),
                    re._removeData(e, n)
                })
            })
        }
    }),
    re.fn.extend({
        queue: function(e, t) {
            var n = 2;
            return "string" != typeof e && (t = e, e = "fx", n--),
            arguments.length < n ? re.queue(this[0], e) : void 0 === t ? this: this.each(function() {
                var n = re.queue(this, e, t);
                re._queueHooks(this, e),
                "fx" === e && "inprogress" !== n[0] && re.dequeue(this, e)
            })
        },
        dequeue: function(e) {
            return this.each(function() {
                re.dequeue(this, e)
            })
        },
        clearQueue: function(e) {
            return this.queue(e || "fx", [])
        },
        promise: function(e, t) {
            var n, i = 1,
            r = re.Deferred(),
            o = this,
            s = this.length,
            a = function() {--i || r.resolveWith(o, [o])
            };
            for ("string" != typeof e && (t = e, e = void 0), e = e || "fx"; s--;) n = re._data(o[s], e + "queueHooks"),
            n && n.empty && (i++, n.empty.add(a));
            return a(),
            r.promise(t)
        }
    });
    var Ee = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source,
    ke = ["Top", "Right", "Bottom", "Left"],
    Se = function(e, t) {
        return e = t || e,
        "none" === re.css(e, "display") || !re.contains(e.ownerDocument, e)
    },
    De = re.access = function(e, t, n, i, r, o, s) {
        var a = 0,
        l = e.length,
        c = null == n;
        if ("object" === re.type(n)) {
            r = !0;
            for (a in n) re.access(e, t, a, n[a], !0, o, s)
        } else if (void 0 !== i && (r = !0, re.isFunction(i) || (s = !0), c && (s ? (t.call(e, i), t = null) : (c = t, t = function(e, t, n) {
            return c.call(re(e), n)
        })), t)) for (; l > a; a++) t(e[a], n, s ? i: i.call(e[a], a, t(e[a], n)));
        return r ? e: c ? t.call(e) : l ? t(e[0], n) : o
    },
    Pe = /^(?:checkbox|radio)$/i; !
    function() {
        var e = he.createElement("input"),
        t = he.createElement("div"),
        n = he.createDocumentFragment();
        if (t.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", ne.leadingWhitespace = 3 === t.firstChild.nodeType, ne.tbody = !t.getElementsByTagName("tbody").length, ne.htmlSerialize = !!t.getElementsByTagName("link").length, ne.html5Clone = "<:nav></:nav>" !== he.createElement("nav").cloneNode(!0).outerHTML, e.type = "checkbox", e.checked = !0, n.appendChild(e), ne.appendChecked = e.checked, t.innerHTML = "<textarea>x</textarea>", ne.noCloneChecked = !!t.cloneNode(!0).lastChild.defaultValue, n.appendChild(t), t.innerHTML = "<input type='radio' checked='checked' name='t'/>", ne.checkClone = t.cloneNode(!0).cloneNode(!0).lastChild.checked, ne.noCloneEvent = !0, t.attachEvent && (t.attachEvent("onclick",
        function() {
            ne.noCloneEvent = !1
        }), t.cloneNode(!0).click()), null == ne.deleteExpando) {
            ne.deleteExpando = !0;
            try {
                delete t.test
            } catch(i) {
                ne.deleteExpando = !1
            }
        }
    } (),
    function() {
        var t, n, i = he.createElement("div");
        for (t in {
            submit: !0,
            change: !0,
            focusin: !0
        }) n = "on" + t,
        (ne[t + "Bubbles"] = n in e) || (i.setAttribute(n, "t"), ne[t + "Bubbles"] = i.attributes[n].expando === !1);
        i = null
    } ();
    var Ae = /^(?:input|select|textarea)$/i,
    je = /^key/,
    Le = /^(?:mouse|pointer|contextmenu)|click/,
    He = /^(?:focusinfocus|focusoutblur)$/,
    Me = /^([^.]*)(?:\.(.+)|)$/;
    re.event = {
        global: {},
        add: function(e, t, n, i, r) {
            var o, s, a, l, c, u, f, p, d, h, g, m = re._data(e);
            if (m) {
                for (n.handler && (l = n, n = l.handler, r = l.selector), n.guid || (n.guid = re.guid++), (s = m.events) || (s = m.events = {}), (u = m.handle) || (u = m.handle = function(e) {
                    return typeof re === _e || e && re.event.triggered === e.type ? void 0 : re.event.dispatch.apply(u.elem, arguments)
                },
                u.elem = e), t = (t || "").match(be) || [""], a = t.length; a--;) o = Me.exec(t[a]) || [],
                d = g = o[1],
                h = (o[2] || "").split(".").sort(),
                d && (c = re.event.special[d] || {},
                d = (r ? c.delegateType: c.bindType) || d, c = re.event.special[d] || {},
                f = re.extend({
                    type: d,
                    origType: g,
                    data: i,
                    handler: n,
                    guid: n.guid,
                    selector: r,
                    needsContext: r && re.expr.match.needsContext.test(r),
                    namespace: h.join(".")
                },
                l), (p = s[d]) || (p = s[d] = [], p.delegateCount = 0, c.setup && c.setup.call(e, i, h, u) !== !1 || (e.addEventListener ? e.addEventListener(d, u, !1) : e.attachEvent && e.attachEvent("on" + d, u))), c.add && (c.add.call(e, f), f.handler.guid || (f.handler.guid = n.guid)), r ? p.splice(p.delegateCount++, 0, f) : p.push(f), re.event.global[d] = !0);
                e = null
            }
        },
        remove: function(e, t, n, i, r) {
            var o, s, a, l, c, u, f, p, d, h, g, m = re.hasData(e) && re._data(e);
            if (m && (u = m.events)) {
                for (t = (t || "").match(be) || [""], c = t.length; c--;) if (a = Me.exec(t[c]) || [], d = g = a[1], h = (a[2] || "").split(".").sort(), d) {
                    for (f = re.event.special[d] || {},
                    d = (i ? f.delegateType: f.bindType) || d, p = u[d] || [], a = a[2] && new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)"), l = o = p.length; o--;) s = p[o],
                    !r && g !== s.origType || n && n.guid !== s.guid || a && !a.test(s.namespace) || i && i !== s.selector && ("**" !== i || !s.selector) || (p.splice(o, 1), s.selector && p.delegateCount--, f.remove && f.remove.call(e, s));
                    l && !p.length && (f.teardown && f.teardown.call(e, h, m.handle) !== !1 || re.removeEvent(e, d, m.handle), delete u[d])
                } else for (d in u) re.event.remove(e, d + t[c], n, i, !0);
                re.isEmptyObject(u) && (delete m.handle, re._removeData(e, "events"))
            }
        },
        trigger: function(t, n, i, r) {
            var o, s, a, l, c, u, f, p = [i || he],
            d = te.call(t, "type") ? t.type: t,
            h = te.call(t, "namespace") ? t.namespace.split(".") : [];
            if (a = u = i = i || he, 3 !== i.nodeType && 8 !== i.nodeType && !He.test(d + re.event.triggered) && (d.indexOf(".") >= 0 && (h = d.split("."), d = h.shift(), h.sort()), s = d.indexOf(":") < 0 && "on" + d, t = t[re.expando] ? t: new re.Event(d, "object" == typeof t && t), t.isTrigger = r ? 2 : 3, t.namespace = h.join("."), t.namespace_re = t.namespace ? new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = void 0, t.target || (t.target = i), n = null == n ? [t] : re.makeArray(n, [t]), c = re.event.special[d] || {},
            r || !c.trigger || c.trigger.apply(i, n) !== !1)) {
                if (!r && !c.noBubble && !re.isWindow(i)) {
                    for (l = c.delegateType || d, He.test(l + d) || (a = a.parentNode); a; a = a.parentNode) p.push(a),
                    u = a;
                    u === (i.ownerDocument || he) && p.push(u.defaultView || u.parentWindow || e)
                }
                for (f = 0; (a = p[f++]) && !t.isPropagationStopped();) t.type = f > 1 ? l: c.bindType || d,
                o = (re._data(a, "events") || {})[t.type] && re._data(a, "handle"),
                o && o.apply(a, n),
                o = s && a[s],
                o && o.apply && re.acceptData(a) && (t.result = o.apply(a, n), t.result === !1 && t.preventDefault());
                if (t.type = d, !r && !t.isDefaultPrevented() && (!c._default || c._default.apply(p.pop(), n) === !1) && re.acceptData(i) && s && i[d] && !re.isWindow(i)) {
                    u = i[s],
                    u && (i[s] = null),
                    re.event.triggered = d;
                    try {
                        i[d]()
                    } catch(g) {}
                    re.event.triggered = void 0,
                    u && (i[s] = u)
                }
                return t.result
            }
        },
        dispatch: function(e) {
            e = re.event.fix(e);
            var t, n, i, r, o, s = [],
            a = Q.call(arguments),
            l = (re._data(this, "events") || {})[e.type] || [],
            c = re.event.special[e.type] || {};
            if (a[0] = e, e.delegateTarget = this, !c.preDispatch || c.preDispatch.call(this, e) !== !1) {
                for (s = re.event.handlers.call(this, e, l), t = 0; (r = s[t++]) && !e.isPropagationStopped();) for (e.currentTarget = r.elem, o = 0; (i = r.handlers[o++]) && !e.isImmediatePropagationStopped();)(!e.namespace_re || e.namespace_re.test(i.namespace)) && (e.handleObj = i, e.data = i.data, n = ((re.event.special[i.origType] || {}).handle || i.handler).apply(r.elem, a), void 0 !== n && (e.result = n) === !1 && (e.preventDefault(), e.stopPropagation()));
                return c.postDispatch && c.postDispatch.call(this, e),
                e.result
            }
        },
        handlers: function(e, t) {
            var n, i, r, o, s = [],
            a = t.delegateCount,
            l = e.target;
            if (a && l.nodeType && (!e.button || "click" !== e.type)) for (; l != this; l = l.parentNode || this) if (1 === l.nodeType && (l.disabled !== !0 || "click" !== e.type)) {
                for (r = [], o = 0; a > o; o++) i = t[o],
                n = i.selector + " ",
                void 0 === r[n] && (r[n] = i.needsContext ? re(n, this).index(l) >= 0 : re.find(n, this, null, [l]).length),
                r[n] && r.push(i);
                r.length && s.push({
                    elem: l,
                    handlers: r
                })
            }
            return a < t.length && s.push({
                elem: this,
                handlers: t.slice(a)
            }),
            s
        },
        fix: function(e) {
            if (e[re.expando]) return e;
            var t, n, i, r = e.type,
            o = e,
            s = this.fixHooks[r];
            for (s || (this.fixHooks[r] = s = Le.test(r) ? this.mouseHooks: je.test(r) ? this.keyHooks: {}), i = s.props ? this.props.concat(s.props) : this.props, e = new re.Event(o), t = i.length; t--;) n = i[t],
            e[n] = o[n];
            return e.target || (e.target = o.srcElement || he),
            3 === e.target.nodeType && (e.target = e.target.parentNode),
            e.metaKey = !!e.metaKey,
            s.filter ? s.filter(e, o) : e
        },
        props: "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "),
            filter: function(e, t) {
                return null == e.which && (e.which = null != t.charCode ? t.charCode: t.keyCode),
                e
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function(e, t) {
                var n, i, r, o = t.button,
                s = t.fromElement;
                return null == e.pageX && null != t.clientX && (i = e.target.ownerDocument || he, r = i.documentElement, n = i.body, e.pageX = t.clientX + (r && r.scrollLeft || n && n.scrollLeft || 0) - (r && r.clientLeft || n && n.clientLeft || 0), e.pageY = t.clientY + (r && r.scrollTop || n && n.scrollTop || 0) - (r && r.clientTop || n && n.clientTop || 0)),
                !e.relatedTarget && s && (e.relatedTarget = s === e.target ? t.toElement: s),
                e.which || void 0 === o || (e.which = 1 & o ? 1 : 2 & o ? 3 : 4 & o ? 2 : 0),
                e
            }
        },
        special: {
            load: {
                noBubble: !0
            },
            focus: {
                trigger: function() {
                    if (this !== h() && this.focus) try {
                        return this.focus(),
                        !1
                    } catch(e) {}
                },
                delegateType: "focusin"
            },
            blur: {
                trigger: function() {
                    return this === h() && this.blur ? (this.blur(), !1) : void 0
                },
                delegateType: "focusout"
            },
            click: {
                trigger: function() {
                    return re.nodeName(this, "input") && "checkbox" === this.type && this.click ? (this.click(), !1) : void 0
                },
                _default: function(e) {
                    return re.nodeName(e.target, "a")
                }
            },
            beforeunload: {
                postDispatch: function(e) {
                    void 0 !== e.result && e.originalEvent && (e.originalEvent.returnValue = e.result)
                }
            }
        },
        simulate: function(e, t, n, i) {
            var r = re.extend(new re.Event, n, {
                type: e,
                isSimulated: !0,
                originalEvent: {}
            });
            i ? re.event.trigger(r, null, t) : re.event.dispatch.call(t, r),
            r.isDefaultPrevented() && n.preventDefault()
        }
    },
    re.removeEvent = he.removeEventListener ?
    function(e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n, !1)
    }: function(e, t, n) {
        var i = "on" + t;
        e.detachEvent && (typeof e[i] === _e && (e[i] = null), e.detachEvent(i, n))
    },
    re.Event = function(e, t) {
        return this instanceof re.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && e.returnValue === !1 ? p: d) : this.type = e, t && re.extend(this, t), this.timeStamp = e && e.timeStamp || re.now(), void(this[re.expando] = !0)) : new re.Event(e, t)
    },
    re.Event.prototype = {
        isDefaultPrevented: d,
        isPropagationStopped: d,
        isImmediatePropagationStopped: d,
        preventDefault: function() {
            var e = this.originalEvent;
            this.isDefaultPrevented = p,
            e && (e.preventDefault ? e.preventDefault() : e.returnValue = !1)
        },
        stopPropagation: function() {
            var e = this.originalEvent;
            this.isPropagationStopped = p,
            e && (e.stopPropagation && e.stopPropagation(), e.cancelBubble = !0)
        },
        stopImmediatePropagation: function() {
            var e = this.originalEvent;
            this.isImmediatePropagationStopped = p,
            e && e.stopImmediatePropagation && e.stopImmediatePropagation(),
            this.stopPropagation()
        }
    },
    re.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout",
        pointerenter: "pointerover",
        pointerleave: "pointerout"
    },
    function(e, t) {
        re.event.special[e] = {
            delegateType: t,
            bindType: t,
            handle: function(e) {
                var n, i = this,
                r = e.relatedTarget,
                o = e.handleObj;
                return (!r || r !== i && !re.contains(i, r)) && (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t),
                n
            }
        }
    }),
    ne.submitBubbles || (re.event.special.submit = {
        setup: function() {
            return re.nodeName(this, "form") ? !1 : void re.event.add(this, "click._submit keypress._submit",
            function(e) {
                var t = e.target,
                n = re.nodeName(t, "input") || re.nodeName(t, "button") ? t.form: void 0;
                n && !re._data(n, "submitBubbles") && (re.event.add(n, "submit._submit",
                function(e) {
                    e._submit_bubble = !0
                }), re._data(n, "submitBubbles", !0))
            })
        },
        postDispatch: function(e) {
            e._submit_bubble && (delete e._submit_bubble, this.parentNode && !e.isTrigger && re.event.simulate("submit", this.parentNode, e, !0))
        },
        teardown: function() {
            return re.nodeName(this, "form") ? !1 : void re.event.remove(this, "._submit")
        }
    }),
    ne.changeBubbles || (re.event.special.change = {
        setup: function() {
            return Ae.test(this.nodeName) ? (("checkbox" === this.type || "radio" === this.type) && (re.event.add(this, "propertychange._change",
            function(e) {
                "checked" === e.originalEvent.propertyName && (this._just_changed = !0)
            }), re.event.add(this, "click._change",
            function(e) {
                this._just_changed && !e.isTrigger && (this._just_changed = !1),
                re.event.simulate("change", this, e, !0)
            })), !1) : void re.event.add(this, "beforeactivate._change",
            function(e) {
                var t = e.target;
                Ae.test(t.nodeName) && !re._data(t, "changeBubbles") && (re.event.add(t, "change._change",
                function(e) { ! this.parentNode || e.isSimulated || e.isTrigger || re.event.simulate("change", this.parentNode, e, !0)
                }), re._data(t, "changeBubbles", !0))
            })
        },
        handle: function(e) {
            var t = e.target;
            return this !== t || e.isSimulated || e.isTrigger || "radio" !== t.type && "checkbox" !== t.type ? e.handleObj.handler.apply(this, arguments) : void 0
        },
        teardown: function() {
            return re.event.remove(this, "._change"),
            !Ae.test(this.nodeName)
        }
    }),
    ne.focusinBubbles || re.each({
        focus: "focusin",
        blur: "focusout"
    },
    function(e, t) {
        var n = function(e) {
            re.event.simulate(t, e.target, re.event.fix(e), !0)
        };
        re.event.special[t] = {
            setup: function() {
                var i = this.ownerDocument || this,
                r = re._data(i, t);
                r || i.addEventListener(e, n, !0),
                re._data(i, t, (r || 0) + 1)
            },
            teardown: function() {
                var i = this.ownerDocument || this,
                r = re._data(i, t) - 1;
                r ? re._data(i, t, r) : (i.removeEventListener(e, n, !0), re._removeData(i, t))
            }
        }
    }),
    re.fn.extend({
        on: function(e, t, n, i, r) {
            var o, s;
            if ("object" == typeof e) {
                "string" != typeof t && (n = n || t, t = void 0);
                for (o in e) this.on(o, t, n, e[o], r);
                return this
            }
            if (null == n && null == i ? (i = t, n = t = void 0) : null == i && ("string" == typeof t ? (i = n, n = void 0) : (i = n, n = t, t = void 0)), i === !1) i = d;
            else if (!i) return this;
            return 1 === r && (s = i, i = function(e) {
                return re().off(e),
                s.apply(this, arguments)
            },
            i.guid = s.guid || (s.guid = re.guid++)),
            this.each(function() {
                re.event.add(this, e, i, n, t)
            })
        },
        one: function(e, t, n, i) {
            return this.on(e, t, n, i, 1)
        },
        off: function(e, t, n) {
            var i, r;
            if (e && e.preventDefault && e.handleObj) return i = e.handleObj,
            re(e.delegateTarget).off(i.namespace ? i.origType + "." + i.namespace: i.origType, i.selector, i.handler),
            this;
            if ("object" == typeof e) {
                for (r in e) this.off(r, t, e[r]);
                return this
            }
            return (t === !1 || "function" == typeof t) && (n = t, t = void 0),
            n === !1 && (n = d),
            this.each(function() {
                re.event.remove(this, e, n, t)
            })
        },
        trigger: function(e, t) {
            return this.each(function() {
                re.event.trigger(e, t, this)
            })
        },
        triggerHandler: function(e, t) {
            var n = this[0];
            return n ? re.event.trigger(e, t, n, !0) : void 0
        }
    });
    var Oe = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",
    We = / jQuery\d+="(?:null|\d+)"/g,
    Ie = new RegExp("<(?:" + Oe + ")[\\s/>]", "i"),
    Fe = /^\s+/,
    qe = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi,
    Be = /<([\w:]+)/,
    Re = /<tbody/i,
    ze = /<|&#?\w+;/,
    $e = /<(?:script|style|link)/i,
    Xe = /checked\s*(?:[^=]|=\s*.checked.)/i,
    Ue = /^$|\/(?:java|ecma)script/i,
    Ye = /^true\/(.*)/,
    Ve = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g,
    Qe = {
        option: [1, "<select multiple='multiple'>", "</select>"],
        legend: [1, "<fieldset>", "</fieldset>"],
        area: [1, "<map>", "</map>"],
        param: [1, "<object>", "</object>"],
        thead: [1, "<table>", "</table>"],
        tr: [2, "<table><tbody>", "</tbody></table>"],
        col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
        td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
        _default: ne.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]
    },
    Ke = g(he),
    Ge = Ke.appendChild(he.createElement("div"));
    Qe.optgroup = Qe.option,
    Qe.tbody = Qe.tfoot = Qe.colgroup = Qe.caption = Qe.thead,
    Qe.th = Qe.td,
    re.extend({
        clone: function(e, t, n) {
            var i, r, o, s, a, l = re.contains(e.ownerDocument, e);
            if (ne.html5Clone || re.isXMLDoc(e) || !Ie.test("<" + e.nodeName + ">") ? o = e.cloneNode(!0) : (Ge.innerHTML = e.outerHTML, Ge.removeChild(o = Ge.firstChild)), !(ne.noCloneEvent && ne.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || re.isXMLDoc(e))) for (i = m(o), a = m(e), s = 0; null != (r = a[s]); ++s) i[s] && _(r, i[s]);
            if (t) if (n) for (a = a || m(e), i = i || m(o), s = 0; null != (r = a[s]); s++) T(r, i[s]);
            else T(e, o);
            return i = m(o, "script"),
            i.length > 0 && w(i, !l && m(e, "script")),
            i = a = r = null,
            o
        },
        buildFragment: function(e, t, n, i) {
            for (var r, o, s, a, l, c, u, f = e.length,
            p = g(t), d = [], h = 0; f > h; h++) if (o = e[h], o || 0 === o) if ("object" === re.type(o)) re.merge(d, o.nodeType ? [o] : o);
            else if (ze.test(o)) {
                for (a = a || p.appendChild(t.createElement("div")), l = (Be.exec(o) || ["", ""])[1].toLowerCase(), u = Qe[l] || Qe._default, a.innerHTML = u[1] + o.replace(qe, "<$1></$2>") + u[2], r = u[0]; r--;) a = a.lastChild;
                if (!ne.leadingWhitespace && Fe.test(o) && d.push(t.createTextNode(Fe.exec(o)[0])), !ne.tbody) for (o = "table" !== l || Re.test(o) ? "<table>" !== u[1] || Re.test(o) ? 0 : a: a.firstChild, r = o && o.childNodes.length; r--;) re.nodeName(c = o.childNodes[r], "tbody") && !c.childNodes.length && o.removeChild(c);
                for (re.merge(d, a.childNodes), a.textContent = ""; a.firstChild;) a.removeChild(a.firstChild);
                a = p.lastChild
            } else d.push(t.createTextNode(o));
            for (a && p.removeChild(a), ne.appendChecked || re.grep(m(d, "input"), v), h = 0; o = d[h++];) if ((!i || -1 === re.inArray(o, i)) && (s = re.contains(o.ownerDocument, o), a = m(p.appendChild(o), "script"), s && w(a), n)) for (r = 0; o = a[r++];) Ue.test(o.type || "") && n.push(o);
            return a = null,
            p
        },
        cleanData: function(e, t) {
            for (var n, i, r, o, s = 0,
            a = re.expando,
            l = re.cache,
            c = ne.deleteExpando,
            u = re.event.special; null != (n = e[s]); s++) if ((t || re.acceptData(n)) && (r = n[a], o = r && l[r])) {
                if (o.events) for (i in o.events) u[i] ? re.event.remove(n, i) : re.removeEvent(n, i, o.handle);
                l[r] && (delete l[r], c ? delete n[a] : typeof n.removeAttribute !== _e ? n.removeAttribute(a) : n[a] = null, V.push(r))
            }
        }
    }),
    re.fn.extend({
        text: function(e) {
            return De(this,
            function(e) {
                return void 0 === e ? re.text(this) : this.empty().append((this[0] && this[0].ownerDocument || he).createTextNode(e))
            },
            null, e, arguments.length)
        },
        append: function() {
            return this.domManip(arguments,
            function(e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = y(this, e);

                    t.appendChild(e)
                }
            })
        },
        prepend: function() {
            return this.domManip(arguments,
            function(e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = y(this, e);
                    t.insertBefore(e, t.firstChild)
                }
            })
        },
        before: function() {
            return this.domManip(arguments,
            function(e) {
                this.parentNode && this.parentNode.insertBefore(e, this)
            })
        },
        after: function() {
            return this.domManip(arguments,
            function(e) {
                this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
            })
        },
        remove: function(e, t) {
            for (var n, i = e ? re.filter(e, this) : this, r = 0; null != (n = i[r]); r++) t || 1 !== n.nodeType || re.cleanData(m(n)),
            n.parentNode && (t && re.contains(n.ownerDocument, n) && w(m(n, "script")), n.parentNode.removeChild(n));
            return this
        },
        empty: function() {
            for (var e, t = 0; null != (e = this[t]); t++) {
                for (1 === e.nodeType && re.cleanData(m(e, !1)); e.firstChild;) e.removeChild(e.firstChild);
                e.options && re.nodeName(e, "select") && (e.options.length = 0)
            }
            return this
        },
        clone: function(e, t) {
            return e = null == e ? !1 : e,
            t = null == t ? e: t,
            this.map(function() {
                return re.clone(this, e, t)
            })
        },
        html: function(e) {
            return De(this,
            function(e) {
                var t = this[0] || {},
                n = 0,
                i = this.length;
                if (void 0 === e) return 1 === t.nodeType ? t.innerHTML.replace(We, "") : void 0;
                if (! ("string" != typeof e || $e.test(e) || !ne.htmlSerialize && Ie.test(e) || !ne.leadingWhitespace && Fe.test(e) || Qe[(Be.exec(e) || ["", ""])[1].toLowerCase()])) {
                    e = e.replace(qe, "<$1></$2>");
                    try {
                        for (; i > n; n++) t = this[n] || {},
                        1 === t.nodeType && (re.cleanData(m(t, !1)), t.innerHTML = e);
                        t = 0
                    } catch(r) {}
                }
                t && this.empty().append(e)
            },
            null, e, arguments.length)
        },
        replaceWith: function() {
            var e = arguments[0];
            return this.domManip(arguments,
            function(t) {
                e = this.parentNode,
                re.cleanData(m(this)),
                e && e.replaceChild(t, this)
            }),
            e && (e.length || e.nodeType) ? this: this.remove()
        },
        detach: function(e) {
            return this.remove(e, !0)
        },
        domManip: function(e, t) {
            e = K.apply([], e);
            var n, i, r, o, s, a, l = 0,
            c = this.length,
            u = this,
            f = c - 1,
            p = e[0],
            d = re.isFunction(p);
            if (d || c > 1 && "string" == typeof p && !ne.checkClone && Xe.test(p)) return this.each(function(n) {
                var i = u.eq(n);
                d && (e[0] = p.call(this, n, i.html())),
                i.domManip(e, t)
            });
            if (c && (a = re.buildFragment(e, this[0].ownerDocument, !1, this), n = a.firstChild, 1 === a.childNodes.length && (a = n), n)) {
                for (o = re.map(m(a, "script"), b), r = o.length; c > l; l++) i = a,
                l !== f && (i = re.clone(i, !0, !0), r && re.merge(o, m(i, "script"))),
                t.call(this[l], i, l);
                if (r) for (s = o[o.length - 1].ownerDocument, re.map(o, x), l = 0; r > l; l++) i = o[l],
                Ue.test(i.type || "") && !re._data(i, "globalEval") && re.contains(s, i) && (i.src ? re._evalUrl && re._evalUrl(i.src) : re.globalEval((i.text || i.textContent || i.innerHTML || "").replace(Ve, "")));
                a = n = null
            }
            return this
        }
    }),
    re.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    },
    function(e, t) {
        re.fn[e] = function(e) {
            for (var n, i = 0,
            r = [], o = re(e), s = o.length - 1; s >= i; i++) n = i === s ? this: this.clone(!0),
            re(o[i])[t](n),
            G.apply(r, n.get());
            return this.pushStack(r)
        }
    });
    var Je, Ze = {}; !
    function() {
        var e;
        ne.shrinkWrapBlocks = function() {
            if (null != e) return e;
            e = !1;
            var t, n, i;
            return n = he.getElementsByTagName("body")[0],
            n && n.style ? (t = he.createElement("div"), i = he.createElement("div"), i.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(i).appendChild(t), typeof t.style.zoom !== _e && (t.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1", t.appendChild(he.createElement("div")).style.width = "5px", e = 3 !== t.offsetWidth), n.removeChild(i), e) : void 0
        }
    } ();
    var et, tt, nt = /^margin/,
    it = new RegExp("^(" + Ee + ")(?!px)[a-z%]+$", "i"),
    rt = /^(top|right|bottom|left)$/;
    e.getComputedStyle ? (et = function(e) {
        return e.ownerDocument.defaultView.getComputedStyle(e, null)
    },
    tt = function(e, t, n) {
        var i, r, o, s, a = e.style;
        return n = n || et(e),
        s = n ? n.getPropertyValue(t) || n[t] : void 0,
        n && ("" !== s || re.contains(e.ownerDocument, e) || (s = re.style(e, t)), it.test(s) && nt.test(t) && (i = a.width, r = a.minWidth, o = a.maxWidth, a.minWidth = a.maxWidth = a.width = s, s = n.width, a.width = i, a.minWidth = r, a.maxWidth = o)),
        void 0 === s ? s: s + ""
    }) : he.documentElement.currentStyle && (et = function(e) {
        return e.currentStyle
    },
    tt = function(e, t, n) {
        var i, r, o, s, a = e.style;
        return n = n || et(e),
        s = n ? n[t] : void 0,
        null == s && a && a[t] && (s = a[t]),
        it.test(s) && !rt.test(t) && (i = a.left, r = e.runtimeStyle, o = r && r.left, o && (r.left = e.currentStyle.left), a.left = "fontSize" === t ? "1em": s, s = a.pixelLeft + "px", a.left = i, o && (r.left = o)),
        void 0 === s ? s: s + "" || "auto"
    }),
    !
    function() {
        function t() {
            var t, n, i, r;
            n = he.getElementsByTagName("body")[0],
            n && n.style && (t = he.createElement("div"), i = he.createElement("div"), i.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(i).appendChild(t), t.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;margin-top:1%;top:1%;border:1px;padding:1px;width:4px;position:absolute", o = s = !1, l = !0, e.getComputedStyle && (o = "1%" !== (e.getComputedStyle(t, null) || {}).top, s = "4px" === (e.getComputedStyle(t, null) || {
                width: "4px"
            }).width, r = t.appendChild(he.createElement("div")), r.style.cssText = t.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0", r.style.marginRight = r.style.width = "0", t.style.width = "1px", l = !parseFloat((e.getComputedStyle(r, null) || {}).marginRight)), t.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", r = t.getElementsByTagName("td"), r[0].style.cssText = "margin:0;border:0;padding:0;display:none", a = 0 === r[0].offsetHeight, a && (r[0].style.display = "", r[1].style.display = "none", a = 0 === r[0].offsetHeight), n.removeChild(i))
        }
        var n, i, r, o, s, a, l;
        n = he.createElement("div"),
        n.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>",
        r = n.getElementsByTagName("a")[0],
        (i = r && r.style) && (i.cssText = "float:left;opacity:.5", ne.opacity = "0.5" === i.opacity, ne.cssFloat = !!i.cssFloat, n.style.backgroundClip = "content-box", n.cloneNode(!0).style.backgroundClip = "", ne.clearCloneStyle = "content-box" === n.style.backgroundClip, ne.boxSizing = "" === i.boxSizing || "" === i.MozBoxSizing || "" === i.WebkitBoxSizing, re.extend(ne, {
            reliableHiddenOffsets: function() {
                return null == a && t(),
                a
            },
            boxSizingReliable: function() {
                return null == s && t(),
                s
            },
            pixelPosition: function() {
                return null == o && t(),
                o
            },
            reliableMarginRight: function() {
                return null == l && t(),
                l
            }
        }))
    } (),
    re.swap = function(e, t, n, i) {
        var r, o, s = {};
        for (o in t) s[o] = e.style[o],
        e.style[o] = t[o];
        r = n.apply(e, i || []);
        for (o in t) e.style[o] = s[o];
        return r
    };
    var ot = /alpha\([^)]*\)/i,
    st = /opacity\s*=\s*([^)]*)/,
    at = /^(none|table(?!-c[ea]).+)/,
    lt = new RegExp("^(" + Ee + ")(.*)$", "i"),
    ct = new RegExp("^([+-])=(" + Ee + ")", "i"),
    ut = {
        position: "absolute",
        visibility: "hidden",
        display: "block"
    },
    ft = {
        letterSpacing: "0",
        fontWeight: "400"
    },
    pt = ["Webkit", "O", "Moz", "ms"];
    re.extend({
        cssHooks: {
            opacity: {
                get: function(e, t) {
                    if (t) {
                        var n = tt(e, "opacity");
                        return "" === n ? "1": n
                    }
                }
            }
        },
        cssNumber: {
            columnCount: !0,
            fillOpacity: !0,
            flexGrow: !0,
            flexShrink: !0,
            fontWeight: !0,
            lineHeight: !0,
            opacity: !0,
            order: !0,
            orphans: !0,
            widows: !0,
            zIndex: !0,
            zoom: !0
        },
        cssProps: {
            "float": ne.cssFloat ? "cssFloat": "styleFloat"
        },
        style: function(e, t, n, i) {
            if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                var r, o, s, a = re.camelCase(t),
                l = e.style;
                if (t = re.cssProps[a] || (re.cssProps[a] = k(l, a)), s = re.cssHooks[t] || re.cssHooks[a], void 0 === n) return s && "get" in s && void 0 !== (r = s.get(e, !1, i)) ? r: l[t];
                if (o = typeof n, "string" === o && (r = ct.exec(n)) && (n = (r[1] + 1) * r[2] + parseFloat(re.css(e, t)), o = "number"), null != n && n === n && ("number" !== o || re.cssNumber[a] || (n += "px"), ne.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (l[t] = "inherit"), !(s && "set" in s && void 0 === (n = s.set(e, n, i))))) try {
                    l[t] = n
                } catch(c) {}
            }
        },
        css: function(e, t, n, i) {
            var r, o, s, a = re.camelCase(t);
            return t = re.cssProps[a] || (re.cssProps[a] = k(e.style, a)),
            s = re.cssHooks[t] || re.cssHooks[a],
            s && "get" in s && (o = s.get(e, !0, n)),
            void 0 === o && (o = tt(e, t, i)),
            "normal" === o && t in ft && (o = ft[t]),
            "" === n || n ? (r = parseFloat(o), n === !0 || re.isNumeric(r) ? r || 0 : o) : o
        }
    }),
    re.each(["height", "width"],
    function(e, t) {
        re.cssHooks[t] = {
            get: function(e, n, i) {
                return n ? at.test(re.css(e, "display")) && 0 === e.offsetWidth ? re.swap(e, ut,
                function() {
                    return A(e, t, i)
                }) : A(e, t, i) : void 0
            },
            set: function(e, n, i) {
                var r = i && et(e);
                return D(e, n, i ? P(e, t, i, ne.boxSizing && "border-box" === re.css(e, "boxSizing", !1, r), r) : 0)
            }
        }
    }),
    ne.opacity || (re.cssHooks.opacity = {
        get: function(e, t) {
            return st.test((t && e.currentStyle ? e.currentStyle.filter: e.style.filter) || "") ? .01 * parseFloat(RegExp.$1) + "": t ? "1": ""
        },
        set: function(e, t) {
            var n = e.style,
            i = e.currentStyle,
            r = re.isNumeric(t) ? "alpha(opacity=" + 100 * t + ")": "",
            o = i && i.filter || n.filter || "";
            n.zoom = 1,
            (t >= 1 || "" === t) && "" === re.trim(o.replace(ot, "")) && n.removeAttribute && (n.removeAttribute("filter"), "" === t || i && !i.filter) || (n.filter = ot.test(o) ? o.replace(ot, r) : o + " " + r)
        }
    }),
    re.cssHooks.marginRight = E(ne.reliableMarginRight,
    function(e, t) {
        return t ? re.swap(e, {
            display: "inline-block"
        },
        tt, [e, "marginRight"]) : void 0
    }),
    re.each({
        margin: "",
        padding: "",
        border: "Width"
    },
    function(e, t) {
        re.cssHooks[e + t] = {
            expand: function(n) {
                for (var i = 0,
                r = {},
                o = "string" == typeof n ? n.split(" ") : [n]; 4 > i; i++) r[e + ke[i] + t] = o[i] || o[i - 2] || o[0];
                return r
            }
        },
        nt.test(e) || (re.cssHooks[e + t].set = D)
    }),
    re.fn.extend({
        css: function(e, t) {
            return De(this,
            function(e, t, n) {
                var i, r, o = {},
                s = 0;
                if (re.isArray(t)) {
                    for (i = et(e), r = t.length; r > s; s++) o[t[s]] = re.css(e, t[s], !1, i);
                    return o
                }
                return void 0 !== n ? re.style(e, t, n) : re.css(e, t)
            },
            e, t, arguments.length > 1)
        },
        show: function() {
            return S(this, !0)
        },
        hide: function() {
            return S(this)
        },
        toggle: function(e) {
            return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function() {
                Se(this) ? re(this).show() : re(this).hide()
            })
        }
    }),
    re.Tween = j,
    j.prototype = {
        constructor: j,
        init: function(e, t, n, i, r, o) {
            this.elem = e,
            this.prop = n,
            this.easing = r || "swing",
            this.options = t,
            this.start = this.now = this.cur(),
            this.end = i,
            this.unit = o || (re.cssNumber[n] ? "": "px")
        },
        cur: function() {
            var e = j.propHooks[this.prop];
            return e && e.get ? e.get(this) : j.propHooks._default.get(this)
        },
        run: function(e) {
            var t, n = j.propHooks[this.prop];
            return this.pos = t = this.options.duration ? re.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : e,
            this.now = (this.end - this.start) * t + this.start,
            this.options.step && this.options.step.call(this.elem, this.now, this),
            n && n.set ? n.set(this) : j.propHooks._default.set(this),
            this
        }
    },
    j.prototype.init.prototype = j.prototype,
    j.propHooks = {
        _default: {
            get: function(e) {
                var t;
                return null == e.elem[e.prop] || e.elem.style && null != e.elem.style[e.prop] ? (t = re.css(e.elem, e.prop, ""), t && "auto" !== t ? t: 0) : e.elem[e.prop]
            },
            set: function(e) {
                re.fx.step[e.prop] ? re.fx.step[e.prop](e) : e.elem.style && (null != e.elem.style[re.cssProps[e.prop]] || re.cssHooks[e.prop]) ? re.style(e.elem, e.prop, e.now + e.unit) : e.elem[e.prop] = e.now
            }
        }
    },
    j.propHooks.scrollTop = j.propHooks.scrollLeft = {
        set: function(e) {
            e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
        }
    },
    re.easing = {
        linear: function(e) {
            return e
        },
        swing: function(e) {
            return.5 - Math.cos(e * Math.PI) / 2
        }
    },
    re.fx = j.prototype.init,
    re.fx.step = {};
    var dt, ht, gt = /^(?:toggle|show|hide)$/,
    mt = new RegExp("^(?:([+-])=|)(" + Ee + ")([a-z%]*)$", "i"),
    vt = /queueHooks$/,
    yt = [O],
    bt = {
        "*": [function(e, t) {
            var n = this.createTween(e, t),
            i = n.cur(),
            r = mt.exec(t),
            o = r && r[3] || (re.cssNumber[e] ? "": "px"),
            s = (re.cssNumber[e] || "px" !== o && +i) && mt.exec(re.css(n.elem, e)),
            a = 1,
            l = 20;
            if (s && s[3] !== o) {
                o = o || s[3],
                r = r || [],
                s = +i || 1;
                do a = a || ".5",
                s /= a,
                re.style(n.elem, e, s + o);
                while (a !== (a = n.cur() / i) && 1 !== a && --l)
            }
            return r && (s = n.start = +s || +i || 0, n.unit = o, n.end = r[1] ? s + (r[1] + 1) * r[2] : +r[2]),
            n
        }]
    };
    re.Animation = re.extend(I, {
        tweener: function(e, t) {
            re.isFunction(e) ? (t = e, e = ["*"]) : e = e.split(" ");
            for (var n, i = 0,
            r = e.length; r > i; i++) n = e[i],
            bt[n] = bt[n] || [],
            bt[n].unshift(t)
        },
        prefilter: function(e, t) {
            t ? yt.unshift(e) : yt.push(e)
        }
    }),
    re.speed = function(e, t, n) {
        var i = e && "object" == typeof e ? re.extend({},
        e) : {
            complete: n || !n && t || re.isFunction(e) && e,
            duration: e,
            easing: n && t || t && !re.isFunction(t) && t
        };
        return i.duration = re.fx.off ? 0 : "number" == typeof i.duration ? i.duration: i.duration in re.fx.speeds ? re.fx.speeds[i.duration] : re.fx.speeds._default,
        (null == i.queue || i.queue === !0) && (i.queue = "fx"),
        i.old = i.complete,
        i.complete = function() {
            re.isFunction(i.old) && i.old.call(this),
            i.queue && re.dequeue(this, i.queue)
        },
        i
    },
    re.fn.extend({
        fadeTo: function(e, t, n, i) {
            return this.filter(Se).css("opacity", 0).show().end().animate({
                opacity: t
            },
            e, n, i)
        },
        animate: function(e, t, n, i) {
            var r = re.isEmptyObject(e),
            o = re.speed(t, n, i),
            s = function() {
                var t = I(this, re.extend({},
                e), o); (r || re._data(this, "finish")) && t.stop(!0)
            };
            return s.finish = s,
            r || o.queue === !1 ? this.each(s) : this.queue(o.queue, s)
        },
        stop: function(e, t, n) {
            var i = function(e) {
                var t = e.stop;
                delete e.stop,
                t(n)
            };
            return "string" != typeof e && (n = t, t = e, e = void 0),
            t && e !== !1 && this.queue(e || "fx", []),
            this.each(function() {
                var t = !0,
                r = null != e && e + "queueHooks",
                o = re.timers,
                s = re._data(this);
                if (r) s[r] && s[r].stop && i(s[r]);
                else for (r in s) s[r] && s[r].stop && vt.test(r) && i(s[r]);
                for (r = o.length; r--;) o[r].elem !== this || null != e && o[r].queue !== e || (o[r].anim.stop(n), t = !1, o.splice(r, 1)); (t || !n) && re.dequeue(this, e)
            })
        },
        finish: function(e) {
            return e !== !1 && (e = e || "fx"),
            this.each(function() {
                var t, n = re._data(this),
                i = n[e + "queue"],
                r = n[e + "queueHooks"],
                o = re.timers,
                s = i ? i.length: 0;
                for (n.finish = !0, re.queue(this, e, []), r && r.stop && r.stop.call(this, !0), t = o.length; t--;) o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                for (t = 0; s > t; t++) i[t] && i[t].finish && i[t].finish.call(this);
                delete n.finish
            })
        }
    }),
    re.each(["toggle", "show", "hide"],
    function(e, t) {
        var n = re.fn[t];
        re.fn[t] = function(e, i, r) {
            return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(H(t, !0), e, i, r)
        }
    }),
    re.each({
        slideDown: H("show"),
        slideUp: H("hide"),
        slideToggle: H("toggle"),
        fadeIn: {
            opacity: "show"
        },
        fadeOut: {
            opacity: "hide"
        },
        fadeToggle: {
            opacity: "toggle"
        }
    },
    function(e, t) {
        re.fn[e] = function(e, n, i) {
            return this.animate(t, e, n, i)
        }
    }),
    re.timers = [],
    re.fx.tick = function() {
        var e, t = re.timers,
        n = 0;
        for (dt = re.now(); n < t.length; n++) e = t[n],
        e() || t[n] !== e || t.splice(n--, 1);
        t.length || re.fx.stop(),
        dt = void 0
    },
    re.fx.timer = function(e) {
        re.timers.push(e),
        e() ? re.fx.start() : re.timers.pop()
    },
    re.fx.interval = 13,
    re.fx.start = function() {
        ht || (ht = setInterval(re.fx.tick, re.fx.interval))
    },
    re.fx.stop = function() {
        clearInterval(ht),
        ht = null
    },
    re.fx.speeds = {
        slow: 600,
        fast: 200,
        _default: 400
    },
    re.fn.delay = function(e, t) {
        return e = re.fx ? re.fx.speeds[e] || e: e,
        t = t || "fx",
        this.queue(t,
        function(t, n) {
            var i = setTimeout(t, e);
            n.stop = function() {
                clearTimeout(i)
            }
        })
    },
    function() {
        var e, t, n, i, r;
        t = he.createElement("div"),
        t.setAttribute("className", "t"),
        t.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>",
        i = t.getElementsByTagName("a")[0],
        n = he.createElement("select"),
        r = n.appendChild(he.createElement("option")),
        e = t.getElementsByTagName("input")[0],
        i.style.cssText = "top:1px",
        ne.getSetAttribute = "t" !== t.className,
        ne.style = /top/.test(i.getAttribute("style")),
        ne.hrefNormalized = "/a" === i.getAttribute("href"),
        ne.checkOn = !!e.value,
        ne.optSelected = r.selected,
        ne.enctype = !!he.createElement("form").enctype,
        n.disabled = !0,
        ne.optDisabled = !r.disabled,
        e = he.createElement("input"),
        e.setAttribute("value", ""),
        ne.input = "" === e.getAttribute("value"),
        e.value = "t",
        e.setAttribute("type", "radio"),
        ne.radioValue = "t" === e.value
    } ();
    var xt = /\r/g;
    re.fn.extend({
        val: function(e) {
            var t, n, i, r = this[0];
            return arguments.length ? (i = re.isFunction(e), this.each(function(n) {
                var r;
                1 === this.nodeType && (r = i ? e.call(this, n, re(this).val()) : e, null == r ? r = "": "number" == typeof r ? r += "": re.isArray(r) && (r = re.map(r,
                function(e) {
                    return null == e ? "": e + ""
                })), t = re.valHooks[this.type] || re.valHooks[this.nodeName.toLowerCase()], t && "set" in t && void 0 !== t.set(this, r, "value") || (this.value = r))
            })) : r ? (t = re.valHooks[r.type] || re.valHooks[r.nodeName.toLowerCase()], t && "get" in t && void 0 !== (n = t.get(r, "value")) ? n: (n = r.value, "string" == typeof n ? n.replace(xt, "") : null == n ? "": n)) : void 0
        }
    }),
    re.extend({
        valHooks: {
            option: {
                get: function(e) {
                    var t = re.find.attr(e, "value");
                    return null != t ? t: re.trim(re.text(e))
                }
            },
            select: {
                get: function(e) {
                    for (var t, n, i = e.options,
                    r = e.selectedIndex,
                    o = "select-one" === e.type || 0 > r,
                    s = o ? null: [], a = o ? r + 1 : i.length, l = 0 > r ? a: o ? r: 0; a > l; l++) if (n = i[l], !(!n.selected && l !== r || (ne.optDisabled ? n.disabled: null !== n.getAttribute("disabled")) || n.parentNode.disabled && re.nodeName(n.parentNode, "optgroup"))) {
                        if (t = re(n).val(), o) return t;
                        s.push(t)
                    }
                    return s
                },
                set: function(e, t) {
                    for (var n, i, r = e.options,
                    o = re.makeArray(t), s = r.length; s--;) if (i = r[s], re.inArray(re.valHooks.option.get(i), o) >= 0) try {
                        i.selected = n = !0
                    } catch(a) {
                        i.scrollHeight
                    } else i.selected = !1;
                    return n || (e.selectedIndex = -1),
                    r
                }
            }
        }
    }),
    re.each(["radio", "checkbox"],
    function() {
        re.valHooks[this] = {
            set: function(e, t) {
                return re.isArray(t) ? e.checked = re.inArray(re(e).val(), t) >= 0 : void 0
            }
        },
        ne.checkOn || (re.valHooks[this].get = function(e) {
            return null === e.getAttribute("value") ? "on": e.value
        })
    });
    var wt, Tt, _t = re.expr.attrHandle,
    Ct = /^(?:checked|selected)$/i,
    Nt = ne.getSetAttribute,
    Et = ne.input;
    re.fn.extend({
        attr: function(e, t) {
            return De(this, re.attr, e, t, arguments.length > 1)
        },
        removeAttr: function(e) {
            return this.each(function() {
                re.removeAttr(this, e)
            })
        }
    }),
    re.extend({
        attr: function(e, t, n) {
            var i, r, o = e.nodeType;
            return e && 3 !== o && 8 !== o && 2 !== o ? typeof e.getAttribute === _e ? re.prop(e, t, n) : (1 === o && re.isXMLDoc(e) || (t = t.toLowerCase(), i = re.attrHooks[t] || (re.expr.match.bool.test(t) ? Tt: wt)), void 0 === n ? i && "get" in i && null !== (r = i.get(e, t)) ? r: (r = re.find.attr(e, t), null == r ? void 0 : r) : null !== n ? i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r: (e.setAttribute(t, n + ""), n) : void re.removeAttr(e, t)) : void 0
        },
        removeAttr: function(e, t) {
            var n, i, r = 0,
            o = t && t.match(be);
            if (o && 1 === e.nodeType) for (; n = o[r++];) i = re.propFix[n] || n,
            re.expr.match.bool.test(n) ? Et && Nt || !Ct.test(n) ? e[i] = !1 : e[re.camelCase("default-" + n)] = e[i] = !1 : re.attr(e, n, ""),
            e.removeAttribute(Nt ? n: i)
        },
        attrHooks: {
            type: {
                set: function(e, t) {
                    if (!ne.radioValue && "radio" === t && re.nodeName(e, "input")) {
                        var n = e.value;
                        return e.setAttribute("type", t),
                        n && (e.value = n),
                        t
                    }
                }
            }
        }
    }),
    Tt = {
        set: function(e, t, n) {
            return t === !1 ? re.removeAttr(e, n) : Et && Nt || !Ct.test(n) ? e.setAttribute(!Nt && re.propFix[n] || n, n) : e[re.camelCase("default-" + n)] = e[n] = !0,
            n
        }
    },
    re.each(re.expr.match.bool.source.match(/\w+/g),
    function(e, t) {
        var n = _t[t] || re.find.attr;
        _t[t] = Et && Nt || !Ct.test(t) ?
        function(e, t, i) {
            var r, o;
            return i || (o = _t[t], _t[t] = r, r = null != n(e, t, i) ? t.toLowerCase() : null, _t[t] = o),
            r
        }: function(e, t, n) {
            return n ? void 0 : e[re.camelCase("default-" + t)] ? t.toLowerCase() : null
        }
    }),
    Et && Nt || (re.attrHooks.value = {
        set: function(e, t, n) {
            return re.nodeName(e, "input") ? void(e.defaultValue = t) : wt && wt.set(e, t, n)
        }
    }),
    Nt || (wt = {
        set: function(e, t, n) {
            var i = e.getAttributeNode(n);
            return i || e.setAttributeNode(i = e.ownerDocument.createAttribute(n)),
            i.value = t += "",
            "value" === n || t === e.getAttribute(n) ? t: void 0
        }
    },
    _t.id = _t.name = _t.coords = function(e, t, n) {
        var i;
        return n ? void 0 : (i = e.getAttributeNode(t)) && "" !== i.value ? i.value: null
    },
    re.valHooks.button = {
        get: function(e, t) {
            var n = e.getAttributeNode(t);
            return n && n.specified ? n.value: void 0
        },
        set: wt.set
    },
    re.attrHooks.contenteditable = {
        set: function(e, t, n) {
            wt.set(e, "" === t ? !1 : t, n)
        }
    },
    re.each(["width", "height"],
    function(e, t) {
        re.attrHooks[t] = {
            set: function(e, n) {
                return "" === n ? (e.setAttribute(t, "auto"), n) : void 0
            }
        }
    })),
    ne.style || (re.attrHooks.style = {
        get: function(e) {
            return e.style.cssText || void 0
        },
        set: function(e, t) {
            return e.style.cssText = t + ""
        }
    });
    var kt = /^(?:input|select|textarea|button|object)$/i,
    St = /^(?:a|area)$/i;
    re.fn.extend({
        prop: function(e, t) {
            return De(this, re.prop, e, t, arguments.length > 1)
        },
        removeProp: function(e) {
            return e = re.propFix[e] || e,
            this.each(function() {
                try {
                    this[e] = void 0,
                    delete this[e]
                } catch(t) {}
            })
        }
    }),
    re.extend({
        propFix: {
            "for": "htmlFor",
            "class": "className"
        },
        prop: function(e, t, n) {
            var i, r, o, s = e.nodeType;
            return e && 3 !== s && 8 !== s && 2 !== s ? (o = 1 !== s || !re.isXMLDoc(e), o && (t = re.propFix[t] || t, r = re.propHooks[t]), void 0 !== n ? r && "set" in r && void 0 !== (i = r.set(e, n, t)) ? i: e[t] = n: r && "get" in r && null !== (i = r.get(e, t)) ? i: e[t]) : void 0
        },
        propHooks: {
            tabIndex: {
                get: function(e) {
                    var t = re.find.attr(e, "tabindex");
                    return t ? parseInt(t, 10) : kt.test(e.nodeName) || St.test(e.nodeName) && e.href ? 0 : -1
                }
            }
        }
    }),
    ne.hrefNormalized || re.each(["href", "src"],
    function(e, t) {
        re.propHooks[t] = {
            get: function(e) {
                return e.getAttribute(t, 4)
            }
        }
    }),
    ne.optSelected || (re.propHooks.selected = {
        get: function(e) {
            var t = e.parentNode;
            return t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex),
            null
        }
    }),
    re.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"],
    function() {
        re.propFix[this.toLowerCase()] = this
    }),
    ne.enctype || (re.propFix.enctype = "encoding");
    var Dt = /[\t\r\n\f]/g;
    re.fn.extend({
        addClass: function(e) {
            var t, n, i, r, o, s, a = 0,
            l = this.length,
            c = "string" == typeof e && e;
            if (re.isFunction(e)) return this.each(function(t) {
                re(this).addClass(e.call(this, t, this.className))
            });
            if (c) for (t = (e || "").match(be) || []; l > a; a++) if (n = this[a], i = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(Dt, " ") : " ")) {
                for (o = 0; r = t[o++];) i.indexOf(" " + r + " ") < 0 && (i += r + " ");
                s = re.trim(i),
                n.className !== s && (n.className = s)
            }
            return this
        },
        removeClass: function(e) {
            var t, n, i, r, o, s, a = 0,
            l = this.length,
            c = 0 === arguments.length || "string" == typeof e && e;
            if (re.isFunction(e)) return this.each(function(t) {
                re(this).removeClass(e.call(this, t, this.className))
            });
            if (c) for (t = (e || "").match(be) || []; l > a; a++) if (n = this[a], i = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(Dt, " ") : "")) {
                for (o = 0; r = t[o++];) for (; i.indexOf(" " + r + " ") >= 0;) i = i.replace(" " + r + " ", " ");
                s = e ? re.trim(i) : "",
                n.className !== s && (n.className = s)
            }
            return this
        },
        toggleClass: function(e, t) {
            var n = typeof e;
            return "boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : this.each(re.isFunction(e) ?
            function(n) {
                re(this).toggleClass(e.call(this, n, this.className, t), t)
            }: function() {
                if ("string" === n) for (var t, i = 0,
                r = re(this), o = e.match(be) || []; t = o[i++];) r.hasClass(t) ? r.removeClass(t) : r.addClass(t);
                else(n === _e || "boolean" === n) && (this.className && re._data(this, "__className__", this.className), this.className = this.className || e === !1 ? "": re._data(this, "__className__") || "")
            })
        },
        hasClass: function(e) {
            for (var t = " " + e + " ",
            n = 0,
            i = this.length; i > n; n++) if (1 === this[n].nodeType && (" " + this[n].className + " ").replace(Dt, " ").indexOf(t) >= 0) return ! 0;
            return ! 1
        }
    }),
    re.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "),
    function(e, t) {
        re.fn[t] = function(e, n) {
            return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }
    }),
    re.fn.extend({
        hover: function(e, t) {
            return this.mouseenter(e).mouseleave(t || e)
        },
        bind: function(e, t, n) {
            return this.on(e, null, t, n)
        },
        unbind: function(e, t) {
            return this.off(e, null, t)
        },
        delegate: function(e, t, n, i) {
            return this.on(t, e, n, i)
        },
        undelegate: function(e, t, n) {
            return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
        }
    });
    var Pt = re.now(),
    At = /\?/,
    jt = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
    re.parseJSON = function(t) {
        if (e.JSON && e.JSON.parse) return e.JSON.parse(t + "");
        var n, i = null,
        r = re.trim(t + "");
        return r && !re.trim(r.replace(jt,
        function(e, t, r, o) {
            return n && t && (i = 0),
            0 === i ? e: (n = r || t, i += !o - !r, "")
        })) ? Function("return " + r)() : re.error("Invalid JSON: " + t)
    },
    re.parseXML = function(t) {
        var n, i;
        if (!t || "string" != typeof t) return null;
        try {
            e.DOMParser ? (i = new DOMParser, n = i.parseFromString(t, "text/xml")) : (n = new ActiveXObject("Microsoft.XMLDOM"), n.async = "false", n.loadXML(t))
        } catch(r) {
            n = void 0
        }
        return n && n.documentElement && !n.getElementsByTagName("parsererror").length || re.error("Invalid XML: " + t),
        n
    };
    var Lt, Ht, Mt = /#.*$/,
    Ot = /([?&])_=[^&]*/,
    Wt = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm,
    It = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/,
    Ft = /^(?:GET|HEAD)$/,
    qt = /^\/\//,
    Bt = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/,
    Rt = {},
    zt = {},
    $t = "*/".concat("*");
    try {
        Ht = location.href
    } catch(Xt) {
        Ht = he.createElement("a"),
        Ht.href = "",
        Ht = Ht.href
    }
    Lt = Bt.exec(Ht.toLowerCase()) || [],
    re.extend({
        active: 0,
        lastModified: {},
        etag: {},
        ajaxSettings: {
            url: Ht,
            type: "GET",
            isLocal: It.test(Lt[1]),
            global: !0,
            processData: !0,
            async: !0,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            accepts: {
                "*": $t,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
            contents: {
                xml: /xml/,
                html: /html/,
                json: /json/
            },
            responseFields: {
                xml: "responseXML",
                text: "responseText",
                json: "responseJSON"
            },
            converters: {
                "* text": String,
                "text html": !0,
                "text json": re.parseJSON,
                "text xml": re.parseXML
            },
            flatOptions: {
                url: !0,
                context: !0
            }
        },
        ajaxSetup: function(e, t) {
            return t ? B(B(e, re.ajaxSettings), t) : B(re.ajaxSettings, e)
        },
        ajaxPrefilter: F(Rt),
        ajaxTransport: F(zt),
        ajax: function(e, t) {
            function n(e, t, n, i) {
                var r, u, v, y, x, T = t;
                2 !== b && (b = 2, a && clearTimeout(a), c = void 0, s = i || "", w.readyState = e > 0 ? 4 : 0, r = e >= 200 && 300 > e || 304 === e, n && (y = R(f, w, n)), y = z(f, y, w, r), r ? (f.ifModified && (x = w.getResponseHeader("Last-Modified"), x && (re.lastModified[o] = x), x = w.getResponseHeader("etag"), x && (re.etag[o] = x)), 204 === e || "HEAD" === f.type ? T = "nocontent": 304 === e ? T = "notmodified": (T = y.state, u = y.data, v = y.error, r = !v)) : (v = T, (e || !T) && (T = "error", 0 > e && (e = 0))), w.status = e, w.statusText = (t || T) + "", r ? h.resolveWith(p, [u, T, w]) : h.rejectWith(p, [w, T, v]), w.statusCode(m), m = void 0, l && d.trigger(r ? "ajaxSuccess": "ajaxError", [w, f, r ? u: v]), g.fireWith(p, [w, T]), l && (d.trigger("ajaxComplete", [w, f]), --re.active || re.event.trigger("ajaxStop")))
            }
            "object" == typeof e && (t = e, e = void 0),
            t = t || {};
            var i, r, o, s, a, l, c, u, f = re.ajaxSetup({},
            t),
            p = f.context || f,
            d = f.context && (p.nodeType || p.jquery) ? re(p) : re.event,
            h = re.Deferred(),
            g = re.Callbacks("once memory"),
            m = f.statusCode || {},
            v = {},
            y = {},
            b = 0,
            x = "canceled",
            w = {
                readyState: 0,
                getResponseHeader: function(e) {
                    var t;
                    if (2 === b) {
                        if (!u) for (u = {}; t = Wt.exec(s);) u[t[1].toLowerCase()] = t[2];
                        t = u[e.toLowerCase()]
                    }
                    return null == t ? null: t
                },
                getAllResponseHeaders: function() {
                    return 2 === b ? s: null
                },
                setRequestHeader: function(e, t) {
                    var n = e.toLowerCase();
                    return b || (e = y[n] = y[n] || e, v[e] = t),
                    this
                },
                overrideMimeType: function(e) {
                    return b || (f.mimeType = e),
                    this
                },
                statusCode: function(e) {
                    var t;
                    if (e) if (2 > b) for (t in e) m[t] = [m[t], e[t]];
                    else w.always(e[w.status]);
                    return this
                },
                abort: function(e) {
                    var t = e || x;
                    return c && c.abort(t),
                    n(0, t),
                    this
                }
            };
            if (h.promise(w).complete = g.add, w.success = w.done, w.error = w.fail, f.url = ((e || f.url || Ht) + "").replace(Mt, "").replace(qt, Lt[1] + "//"), f.type = t.method || t.type || f.method || f.type, f.dataTypes = re.trim(f.dataType || "*").toLowerCase().match(be) || [""], null == f.crossDomain && (i = Bt.exec(f.url.toLowerCase()), f.crossDomain = !(!i || i[1] === Lt[1] && i[2] === Lt[2] && (i[3] || ("http:" === i[1] ? "80": "443")) === (Lt[3] || ("http:" === Lt[1] ? "80": "443")))), f.data && f.processData && "string" != typeof f.data && (f.data = re.param(f.data, f.traditional)), q(Rt, f, t, w), 2 === b) return w;
            l = f.global,
            l && 0 === re.active++&&re.event.trigger("ajaxStart"),
            f.type = f.type.toUpperCase(),
            f.hasContent = !Ft.test(f.type),
            o = f.url,
            f.hasContent || (f.data && (o = f.url += (At.test(o) ? "&": "?") + f.data, delete f.data), f.cache === !1 && (f.url = Ot.test(o) ? o.replace(Ot, "$1_=" + Pt++) : o + (At.test(o) ? "&": "?") + "_=" + Pt++)),
            f.ifModified && (re.lastModified[o] && w.setRequestHeader("If-Modified-Since", re.lastModified[o]), re.etag[o] && w.setRequestHeader("If-None-Match", re.etag[o])),
            (f.data && f.hasContent && f.contentType !== !1 || t.contentType) && w.setRequestHeader("Content-Type", f.contentType),
            w.setRequestHeader("Accept", f.dataTypes[0] && f.accepts[f.dataTypes[0]] ? f.accepts[f.dataTypes[0]] + ("*" !== f.dataTypes[0] ? ", " + $t + "; q=0.01": "") : f.accepts["*"]);
            for (r in f.headers) w.setRequestHeader(r, f.headers[r]);
            if (f.beforeSend && (f.beforeSend.call(p, w, f) === !1 || 2 === b)) return w.abort();
            x = "abort";
            for (r in {
                success: 1,
                error: 1,
                complete: 1
            }) w[r](f[r]);
            if (c = q(zt, f, t, w)) {
                w.readyState = 1,
                l && d.trigger("ajaxSend", [w, f]),
                f.async && f.timeout > 0 && (a = setTimeout(function() {
                    w.abort("timeout")
                },
                f.timeout));
                try {
                    b = 1,
                    c.send(v, n)
                } catch(T) {
                    if (! (2 > b)) throw T;
                    n( - 1, T)
                }
            } else n( - 1, "No Transport");
            return w
        },
        getJSON: function(e, t, n) {
            return re.get(e, t, n, "json")
        },
        getScript: function(e, t) {
            return re.get(e, void 0, t, "script")
        }
    }),
    re.each(["get", "post"],
    function(e, t) {
        re[t] = function(e, n, i, r) {
            return re.isFunction(n) && (r = r || i, i = n, n = void 0),
            re.ajax({
                url: e,
                type: t,
                dataType: r,
                data: n,
                success: i
            })
        }
    }),
    re.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"],
    function(e, t) {
        re.fn[t] = function(e) {
            return this.on(t, e)
        }
    }),
    re._evalUrl = function(e) {
        return re.ajax({
            url: e,
            type: "GET",
            dataType: "script",
            async: !1,
            global: !1,
            "throws": !0
        })
    },
    re.fn.extend({
        wrapAll: function(e) {
            if (re.isFunction(e)) return this.each(function(t) {
                re(this).wrapAll(e.call(this, t))
            });
            if (this[0]) {
                var t = re(e, this[0].ownerDocument).eq(0).clone(!0);
                this[0].parentNode && t.insertBefore(this[0]),
                t.map(function() {
                    for (var e = this; e.firstChild && 1 === e.firstChild.nodeType;) e = e.firstChild;
                    return e
                }).append(this)
            }
            return this
        },
        wrapInner: function(e) {
            return this.each(re.isFunction(e) ?
            function(t) {
                re(this).wrapInner(e.call(this, t))
            }: function() {
                var t = re(this),
                n = t.contents();
                n.length ? n.wrapAll(e) : t.append(e)
            })
        },
        wrap: function(e) {
            var t = re.isFunction(e);
            return this.each(function(n) {
                re(this).wrapAll(t ? e.call(this, n) : e)
            })
        },
        unwrap: function() {
            return this.parent().each(function() {
                re.nodeName(this, "body") || re(this).replaceWith(this.childNodes)
            }).end()
        }
    }),
    re.expr.filters.hidden = function(e) {
        return e.offsetWidth <= 0 && e.offsetHeight <= 0 || !ne.reliableHiddenOffsets() && "none" === (e.style && e.style.display || re.css(e, "display"))
    },
    re.expr.filters.visible = function(e) {
        return ! re.expr.filters.hidden(e)
    };
    var Ut = /%20/g,
    Yt = /\[\]$/,
    Vt = /\r?\n/g,
    Qt = /^(?:submit|button|image|reset|file)$/i,
    Kt = /^(?:input|select|textarea|keygen)/i;
    re.param = function(e, t) {
        var n, i = [],
        r = function(e, t) {
            t = re.isFunction(t) ? t() : null == t ? "": t,
            i[i.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
        };
        if (void 0 === t && (t = re.ajaxSettings && re.ajaxSettings.traditional), re.isArray(e) || e.jquery && !re.isPlainObject(e)) re.each(e,
        function() {
            r(this.name, this.value)
        });
        else for (n in e) $(n, e[n], t, r);
        return i.join("&").replace(Ut, "+")
    },
    re.fn.extend({
        serialize: function() {
            return re.param(this.serializeArray())
        },
        serializeArray: function() {
            return this.map(function() {
                var e = re.prop(this, "elements");
                return e ? re.makeArray(e) : this
            }).filter(function() {
                var e = this.type;
                return this.name && !re(this).is(":disabled") && Kt.test(this.nodeName) && !Qt.test(e) && (this.checked || !Pe.test(e))
            }).map(function(e, t) {
                var n = re(this).val();
                return null == n ? null: re.isArray(n) ? re.map(n,
                function(e) {
                    return {
                        name: t.name,
                        value: e.replace(Vt, "\r\n")
                    }
                }) : {
                    name: t.name,
                    value: n.replace(Vt, "\r\n")
                }
            }).get()
        }
    }),
    re.ajaxSettings.xhr = void 0 !== e.ActiveXObject ?
    function() {
        return ! this.isLocal && /^(get|post|head|put|delete|options)$/i.test(this.type) && X() || U()
    }: X;
    var Gt = 0,
    Jt = {},
    Zt = re.ajaxSettings.xhr();
    e.ActiveXObject && re(e).on("unload",
    function() {
        for (var e in Jt) Jt[e](void 0, !0)
    }),
    ne.cors = !!Zt && "withCredentials" in Zt,
    Zt = ne.ajax = !!Zt,
    Zt && re.ajaxTransport(function(e) {
        if (!e.crossDomain || ne.cors) {
            var t;
            return {
                send: function(n, i) {
                    var r, o = e.xhr(),
                    s = ++Gt;
                    if (o.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields) for (r in e.xhrFields) o[r] = e.xhrFields[r];
                    e.mimeType && o.overrideMimeType && o.overrideMimeType(e.mimeType),
                    e.crossDomain || n["X-Requested-With"] || (n["X-Requested-With"] = "XMLHttpRequest");
                    for (r in n) void 0 !== n[r] && o.setRequestHeader(r, n[r] + "");
                    o.send(e.hasContent && e.data || null),
                    t = function(n, r) {
                        var a, l, c;
                        if (t && (r || 4 === o.readyState)) if (delete Jt[s], t = void 0, o.onreadystatechange = re.noop, r) 4 !== o.readyState && o.abort();
                        else {
                            c = {},
                            a = o.status,
                            "string" == typeof o.responseText && (c.text = o.responseText);
                            try {
                                l = o.statusText
                            } catch(u) {
                                l = ""
                            }
                            a || !e.isLocal || e.crossDomain ? 1223 === a && (a = 204) : a = c.text ? 200 : 404
                        }
                        c && i(a, l, c, o.getAllResponseHeaders())
                    },
                    e.async ? 4 === o.readyState ? setTimeout(t) : o.onreadystatechange = Jt[s] = t: t()
                },
                abort: function() {
                    t && t(void 0, !0)
                }
            }
        }
    }),
    re.ajaxSetup({
        accepts: {
            script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"
        },
        contents: {
            script: /(?:java|ecma)script/
        },
        converters: {
            "text script": function(e) {
                return re.globalEval(e),
                e
            }
        }
    }),
    re.ajaxPrefilter("script",
    function(e) {
        void 0 === e.cache && (e.cache = !1),
        e.crossDomain && (e.type = "GET", e.global = !1)
    }),
    re.ajaxTransport("script",
    function(e) {
        if (e.crossDomain) {
            var t, n = he.head || re("head")[0] || he.documentElement;
            return {
                send: function(i, r) {
                    t = he.createElement("script"),
                    t.async = !0,
                    e.scriptCharset && (t.charset = e.scriptCharset),
                    t.src = e.url,
                    t.onload = t.onreadystatechange = function(e, n) { (n || !t.readyState || /loaded|complete/.test(t.readyState)) && (t.onload = t.onreadystatechange = null, t.parentNode && t.parentNode.removeChild(t), t = null, n || r(200, "success"))
                    },
                    n.insertBefore(t, n.firstChild)
                },
                abort: function() {
                    t && t.onload(void 0, !0)
                }
            }
        }
    });
    var en = [],
    tn = /(=)\?(?=&|$)|\?\?/;
    re.ajaxSetup({
        jsonp: "callback",
        jsonpCallback: function() {
            var e = en.pop() || re.expando + "_" + Pt++;
            return this[e] = !0,
            e
        }
    }),
    re.ajaxPrefilter("json jsonp",
    function(t, n, i) {
        var r, o, s, a = t.jsonp !== !1 && (tn.test(t.url) ? "url": "string" == typeof t.data && !(t.contentType || "").indexOf("application/x-www-form-urlencoded") && tn.test(t.data) && "data");
        return a || "jsonp" === t.dataTypes[0] ? (r = t.jsonpCallback = re.isFunction(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, a ? t[a] = t[a].replace(tn, "$1" + r) : t.jsonp !== !1 && (t.url += (At.test(t.url) ? "&": "?") + t.jsonp + "=" + r), t.converters["script json"] = function() {
            return s || re.error(r + " was not called"),
            s[0]
        },
        t.dataTypes[0] = "json", o = e[r], e[r] = function() {
            s = arguments
        },
        i.always(function() {
            e[r] = o,
            t[r] && (t.jsonpCallback = n.jsonpCallback, en.push(r)),
            s && re.isFunction(o) && o(s[0]),
            s = o = void 0
        }), "script") : void 0
    }),
    re.parseHTML = function(e, t, n) {
        if (!e || "string" != typeof e) return null;
        "boolean" == typeof t && (n = t, t = !1),
        t = t || he;
        var i = fe.exec(e),
        r = !n && [];
        return i ? [t.createElement(i[1])] : (i = re.buildFragment([e], t, r), r && r.length && re(r).remove(), re.merge([], i.childNodes))
    };
    var nn = re.fn.load;
    re.fn.load = function(e, t, n) {
        if ("string" != typeof e && nn) return nn.apply(this, arguments);
        var i, r, o, s = this,
        a = e.indexOf(" ");
        return a >= 0 && (i = re.trim(e.slice(a, e.length)), e = e.slice(0, a)),
        re.isFunction(t) ? (n = t, t = void 0) : t && "object" == typeof t && (o = "POST"),
        s.length > 0 && re.ajax({
            url: e,
            type: o,
            dataType: "html",
            data: t
        }).done(function(e) {
            r = arguments,
            s.html(i ? re("<div>").append(re.parseHTML(e)).find(i) : e)
        }).complete(n &&
        function(e, t) {
            s.each(n, r || [e.responseText, t, e])
        }),
        this
    },
    re.expr.filters.animated = function(e) {
        return re.grep(re.timers,
        function(t) {
            return e === t.elem
        }).length
    };
    var rn = e.document.documentElement;
    re.offset = {
        setOffset: function(e, t, n) {
            var i, r, o, s, a, l, c, u = re.css(e, "position"),
            f = re(e),
            p = {};
            "static" === u && (e.style.position = "relative"),
            a = f.offset(),
            o = re.css(e, "top"),
            l = re.css(e, "left"),
            c = ("absolute" === u || "fixed" === u) && re.inArray("auto", [o, l]) > -1,
            c ? (i = f.position(), s = i.top, r = i.left) : (s = parseFloat(o) || 0, r = parseFloat(l) || 0),
            re.isFunction(t) && (t = t.call(e, n, a)),
            null != t.top && (p.top = t.top - a.top + s),
            null != t.left && (p.left = t.left - a.left + r),
            "using" in t ? t.using.call(e, p) : f.css(p)
        }
    },
    re.fn.extend({
        offset: function(e) {
            if (arguments.length) return void 0 === e ? this: this.each(function(t) {
                re.offset.setOffset(this, e, t)
            });
            var t, n, i = {
                top: 0,
                left: 0
            },
            r = this[0],
            o = r && r.ownerDocument;
            return o ? (t = o.documentElement, re.contains(t, r) ? (typeof r.getBoundingClientRect !== _e && (i = r.getBoundingClientRect()), n = Y(o), {
                top: i.top + (n.pageYOffset || t.scrollTop) - (t.clientTop || 0),
                left: i.left + (n.pageXOffset || t.scrollLeft) - (t.clientLeft || 0)
            }) : i) : void 0
        },
        position: function() {
            if (this[0]) {
                var e, t, n = {
                    top: 0,
                    left: 0
                },
                i = this[0];
                return "fixed" === re.css(i, "position") ? t = i.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), re.nodeName(e[0], "html") || (n = e.offset()), n.top += re.css(e[0], "borderTopWidth", !0), n.left += re.css(e[0], "borderLeftWidth", !0)),
                {
                    top: t.top - n.top - re.css(i, "marginTop", !0),
                    left: t.left - n.left - re.css(i, "marginLeft", !0)
                }
            }
        },
        offsetParent: function() {
            return this.map(function() {
                for (var e = this.offsetParent || rn; e && !re.nodeName(e, "html") && "static" === re.css(e, "position");) e = e.offsetParent;
                return e || rn
            })
        }
    }),
    re.each({
        scrollLeft: "pageXOffset",
        scrollTop: "pageYOffset"
    },
    function(e, t) {
        var n = /Y/.test(t);
        re.fn[e] = function(i) {
            return De(this,
            function(e, i, r) {
                var o = Y(e);
                return void 0 === r ? o ? t in o ? o[t] : o.document.documentElement[i] : e[i] : void(o ? o.scrollTo(n ? re(o).scrollLeft() : r, n ? r: re(o).scrollTop()) : e[i] = r)
            },
            e, i, arguments.length, null)
        }
    }),
    re.each(["top", "left"],
    function(e, t) {
        re.cssHooks[t] = E(ne.pixelPosition,
        function(e, n) {
            return n ? (n = tt(e, t), it.test(n) ? re(e).position()[t] + "px": n) : void 0
        })
    }),
    re.each({
        Height: "height",
        Width: "width"
    },
    function(e, t) {
        re.each({
            padding: "inner" + e,
            content: t,
            "": "outer" + e
        },
        function(n, i) {
            re.fn[i] = function(i, r) {
                var o = arguments.length && (n || "boolean" != typeof i),
                s = n || (i === !0 || r === !0 ? "margin": "border");
                return De(this,
                function(t, n, i) {
                    var r;
                    return re.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (r = t.documentElement, Math.max(t.body["scroll" + e], r["scroll" + e], t.body["offset" + e], r["offset" + e], r["client" + e])) : void 0 === i ? re.css(t, n, s) : re.style(t, n, i, s);
                },
                t, o ? i: void 0, o, null)
            }
        })
    }),
    re.fn.size = function() {
        return this.length
    },
    re.fn.andSelf = re.fn.addBack,
    "function" == typeof define && define.amd && define("jquery", [],
    function() {
        return re
    });
    var on = e.jQuery,
    sn = e.$;
    return re.noConflict = function(t) {
        return e.$ === re && (e.$ = sn),
        t && e.jQuery === re && (e.jQuery = on),
        re
    },
    typeof t === _e && (e.jQuery = e.$ = re),
    re
}),
function() {
    function e(e) {
        function t(t, n, i, r, o, s) {
            for (; o >= 0 && s > o; o += e) {
                var a = r ? r[o] : o;
                i = n(i, t[a], a, t)
            }
            return i
        }
        return function(n, i, r, o) {
            i = b(i, o, 4);
            var s = !E(n) && y.keys(n),
            a = (s || n).length,
            l = e > 0 ? 0 : a - 1;
            return arguments.length < 3 && (r = n[s ? s[l] : l], l += e),
            t(n, i, r, s, l, a)
        }
    }
    function t(e) {
        return function(t, n, i) {
            n = x(n, i);
            for (var r = N(t), o = e > 0 ? 0 : r - 1; o >= 0 && r > o; o += e) if (n(t[o], o, t)) return o;
            return - 1
        }
    }
    function n(e, t, n) {
        return function(i, r, o) {
            var s = 0,
            a = N(i);
            if ("number" == typeof o) e > 0 ? s = o >= 0 ? o: Math.max(o + a, s) : a = o >= 0 ? Math.min(o + 1, a) : o + a + 1;
            else if (n && o && a) return o = n(i, r),
            i[o] === r ? o: -1;
            if (r !== r) return o = t(u.call(i, s, a), y.isNaN),
            o >= 0 ? o + s: -1;
            for (o = e > 0 ? s: a - 1; o >= 0 && a > o; o += e) if (i[o] === r) return o;
            return - 1
        }
    }
    function i(e, t) {
        var n = A.length,
        i = e.constructor,
        r = y.isFunction(i) && i.prototype || a,
        o = "constructor";
        for (y.has(e, o) && !y.contains(t, o) && t.push(o); n--;) o = A[n],
        o in e && e[o] !== r[o] && !y.contains(t, o) && t.push(o)
    }
    var r = this,
    o = r._,
    s = Array.prototype,
    a = Object.prototype,
    l = Function.prototype,
    c = s.push,
    u = s.slice,
    f = a.toString,
    p = a.hasOwnProperty,
    d = Array.isArray,
    h = Object.keys,
    g = l.bind,
    m = Object.create,
    v = function() {},
    y = function(e) {
        return e instanceof y ? e: this instanceof y ? void(this._wrapped = e) : new y(e)
    };
    "undefined" != typeof exports ? ("undefined" != typeof module && module.exports && (exports = module.exports = y), exports._ = y) : r._ = y,
    y.VERSION = "1.8.3";
    var b = function(e, t, n) {
        if (void 0 === t) return e;
        switch (null == n ? 3 : n) {
        case 1:
            return function(n) {
                return e.call(t, n)
            };
        case 2:
            return function(n, i) {
                return e.call(t, n, i)
            };
        case 3:
            return function(n, i, r) {
                return e.call(t, n, i, r)
            };
        case 4:
            return function(n, i, r, o) {
                return e.call(t, n, i, r, o)
            }
        }
        return function() {
            return e.apply(t, arguments)
        }
    },
    x = function(e, t, n) {
        return null == e ? y.identity: y.isFunction(e) ? b(e, t, n) : y.isObject(e) ? y.matcher(e) : y.property(e)
    };
    y.iteratee = function(e, t) {
        return x(e, t, 1 / 0)
    };
    var w = function(e, t) {
        return function(n) {
            var i = arguments.length;
            if (2 > i || null == n) return n;
            for (var r = 1; i > r; r++) for (var o = arguments[r], s = e(o), a = s.length, l = 0; a > l; l++) {
                var c = s[l];
                t && void 0 !== n[c] || (n[c] = o[c])
            }
            return n
        }
    },
    T = function(e) {
        if (!y.isObject(e)) return {};
        if (m) return m(e);
        v.prototype = e;
        var t = new v;
        return v.prototype = null,
        t
    },
    _ = function(e) {
        return function(t) {
            return null == t ? void 0 : t[e]
        }
    },
    C = Math.pow(2, 53) - 1,
    N = _("length"),
    E = function(e) {
        var t = N(e);
        return "number" == typeof t && t >= 0 && C >= t
    };
    y.each = y.forEach = function(e, t, n) {
        t = b(t, n);
        var i, r;
        if (E(e)) for (i = 0, r = e.length; r > i; i++) t(e[i], i, e);
        else {
            var o = y.keys(e);
            for (i = 0, r = o.length; r > i; i++) t(e[o[i]], o[i], e)
        }
        return e
    },
    y.map = y.collect = function(e, t, n) {
        t = x(t, n);
        for (var i = !E(e) && y.keys(e), r = (i || e).length, o = Array(r), s = 0; r > s; s++) {
            var a = i ? i[s] : s;
            o[s] = t(e[a], a, e)
        }
        return o
    },
    y.reduce = y.foldl = y.inject = e(1),
    y.reduceRight = y.foldr = e( - 1),
    y.find = y.detect = function(e, t, n) {
        var i;
        return i = E(e) ? y.findIndex(e, t, n) : y.findKey(e, t, n),
        void 0 !== i && -1 !== i ? e[i] : void 0
    },
    y.filter = y.select = function(e, t, n) {
        var i = [];
        return t = x(t, n),
        y.each(e,
        function(e, n, r) {
            t(e, n, r) && i.push(e)
        }),
        i
    },
    y.reject = function(e, t, n) {
        return y.filter(e, y.negate(x(t)), n)
    },
    y.every = y.all = function(e, t, n) {
        t = x(t, n);
        for (var i = !E(e) && y.keys(e), r = (i || e).length, o = 0; r > o; o++) {
            var s = i ? i[o] : o;
            if (!t(e[s], s, e)) return ! 1
        }
        return ! 0
    },
    y.some = y.any = function(e, t, n) {
        t = x(t, n);
        for (var i = !E(e) && y.keys(e), r = (i || e).length, o = 0; r > o; o++) {
            var s = i ? i[o] : o;
            if (t(e[s], s, e)) return ! 0
        }
        return ! 1
    },
    y.contains = y.includes = y.include = function(e, t, n, i) {
        return E(e) || (e = y.values(e)),
        ("number" != typeof n || i) && (n = 0),
        y.indexOf(e, t, n) >= 0
    },
    y.invoke = function(e, t) {
        var n = u.call(arguments, 2),
        i = y.isFunction(t);
        return y.map(e,
        function(e) {
            var r = i ? t: e[t];
            return null == r ? r: r.apply(e, n)
        })
    },
    y.pluck = function(e, t) {
        return y.map(e, y.property(t))
    },
    y.where = function(e, t) {
        return y.filter(e, y.matcher(t))
    },
    y.findWhere = function(e, t) {
        return y.find(e, y.matcher(t))
    },
    y.max = function(e, t, n) {
        var i, r, o = -1 / 0,
        s = -1 / 0;
        if (null == t && null != e) {
            e = E(e) ? e: y.values(e);
            for (var a = 0,
            l = e.length; l > a; a++) i = e[a],
            i > o && (o = i)
        } else t = x(t, n),
        y.each(e,
        function(e, n, i) {
            r = t(e, n, i),
            (r > s || r === -1 / 0 && o === -1 / 0) && (o = e, s = r)
        });
        return o
    },
    y.min = function(e, t, n) {
        var i, r, o = 1 / 0,
        s = 1 / 0;
        if (null == t && null != e) {
            e = E(e) ? e: y.values(e);
            for (var a = 0,
            l = e.length; l > a; a++) i = e[a],
            o > i && (o = i)
        } else t = x(t, n),
        y.each(e,
        function(e, n, i) {
            r = t(e, n, i),
            (s > r || 1 / 0 === r && 1 / 0 === o) && (o = e, s = r)
        });
        return o
    },
    y.shuffle = function(e) {
        for (var t, n = E(e) ? e: y.values(e), i = n.length, r = Array(i), o = 0; i > o; o++) t = y.random(0, o),
        t !== o && (r[o] = r[t]),
        r[t] = n[o];
        return r
    },
    y.sample = function(e, t, n) {
        return null == t || n ? (E(e) || (e = y.values(e)), e[y.random(e.length - 1)]) : y.shuffle(e).slice(0, Math.max(0, t))
    },
    y.sortBy = function(e, t, n) {
        return t = x(t, n),
        y.pluck(y.map(e,
        function(e, n, i) {
            return {
                value: e,
                index: n,
                criteria: t(e, n, i)
            }
        }).sort(function(e, t) {
            var n = e.criteria,
            i = t.criteria;
            if (n !== i) {
                if (n > i || void 0 === n) return 1;
                if (i > n || void 0 === i) return - 1
            }
            return e.index - t.index
        }), "value")
    };
    var k = function(e) {
        return function(t, n, i) {
            var r = {};
            return n = x(n, i),
            y.each(t,
            function(i, o) {
                var s = n(i, o, t);
                e(r, i, s)
            }),
            r
        }
    };
    y.groupBy = k(function(e, t, n) {
        y.has(e, n) ? e[n].push(t) : e[n] = [t]
    }),
    y.indexBy = k(function(e, t, n) {
        e[n] = t
    }),
    y.countBy = k(function(e, t, n) {
        y.has(e, n) ? e[n]++:e[n] = 1
    }),
    y.toArray = function(e) {
        return e ? y.isArray(e) ? u.call(e) : E(e) ? y.map(e, y.identity) : y.values(e) : []
    },
    y.size = function(e) {
        return null == e ? 0 : E(e) ? e.length: y.keys(e).length
    },
    y.partition = function(e, t, n) {
        t = x(t, n);
        var i = [],
        r = [];
        return y.each(e,
        function(e, n, o) { (t(e, n, o) ? i: r).push(e)
        }),
        [i, r]
    },
    y.first = y.head = y.take = function(e, t, n) {
        return null == e ? void 0 : null == t || n ? e[0] : y.initial(e, e.length - t)
    },
    y.initial = function(e, t, n) {
        return u.call(e, 0, Math.max(0, e.length - (null == t || n ? 1 : t)))
    },
    y.last = function(e, t, n) {
        return null == e ? void 0 : null == t || n ? e[e.length - 1] : y.rest(e, Math.max(0, e.length - t))
    },
    y.rest = y.tail = y.drop = function(e, t, n) {
        return u.call(e, null == t || n ? 1 : t)
    },
    y.compact = function(e) {
        return y.filter(e, y.identity)
    };
    var S = function(e, t, n, i) {
        for (var r = [], o = 0, s = i || 0, a = N(e); a > s; s++) {
            var l = e[s];
            if (E(l) && (y.isArray(l) || y.isArguments(l))) {
                t || (l = S(l, t, n));
                var c = 0,
                u = l.length;
                for (r.length += u; u > c;) r[o++] = l[c++]
            } else n || (r[o++] = l)
        }
        return r
    };
    y.flatten = function(e, t) {
        return S(e, t, !1)
    },
    y.without = function(e) {
        return y.difference(e, u.call(arguments, 1))
    },
    y.uniq = y.unique = function(e, t, n, i) {
        y.isBoolean(t) || (i = n, n = t, t = !1),
        null != n && (n = x(n, i));
        for (var r = [], o = [], s = 0, a = N(e); a > s; s++) {
            var l = e[s],
            c = n ? n(l, s, e) : l;
            t ? (s && o === c || r.push(l), o = c) : n ? y.contains(o, c) || (o.push(c), r.push(l)) : y.contains(r, l) || r.push(l)
        }
        return r
    },
    y.union = function() {
        return y.uniq(S(arguments, !0, !0))
    },
    y.intersection = function(e) {
        for (var t = [], n = arguments.length, i = 0, r = N(e); r > i; i++) {
            var o = e[i];
            if (!y.contains(t, o)) {
                for (var s = 1; n > s && y.contains(arguments[s], o); s++);
                s === n && t.push(o)
            }
        }
        return t
    },
    y.difference = function(e) {
        var t = S(arguments, !0, !0, 1);
        return y.filter(e,
        function(e) {
            return ! y.contains(t, e)
        })
    },
    y.zip = function() {
        return y.unzip(arguments)
    },
    y.unzip = function(e) {
        for (var t = e && y.max(e, N).length || 0, n = Array(t), i = 0; t > i; i++) n[i] = y.pluck(e, i);
        return n
    },
    y.object = function(e, t) {
        for (var n = {},
        i = 0,
        r = N(e); r > i; i++) t ? n[e[i]] = t[i] : n[e[i][0]] = e[i][1];
        return n
    },
    y.findIndex = t(1),
    y.findLastIndex = t( - 1),
    y.sortedIndex = function(e, t, n, i) {
        n = x(n, i, 1);
        for (var r = n(t), o = 0, s = N(e); s > o;) {
            var a = Math.floor((o + s) / 2);
            n(e[a]) < r ? o = a + 1 : s = a
        }
        return o
    },
    y.indexOf = n(1, y.findIndex, y.sortedIndex),
    y.lastIndexOf = n( - 1, y.findLastIndex),
    y.range = function(e, t, n) {
        null == t && (t = e || 0, e = 0),
        n = n || 1;
        for (var i = Math.max(Math.ceil((t - e) / n), 0), r = Array(i), o = 0; i > o; o++, e += n) r[o] = e;
        return r
    };
    var D = function(e, t, n, i, r) {
        if (! (i instanceof t)) return e.apply(n, r);
        var o = T(e.prototype),
        s = e.apply(o, r);
        return y.isObject(s) ? s: o
    };
    y.bind = function(e, t) {
        if (g && e.bind === g) return g.apply(e, u.call(arguments, 1));
        if (!y.isFunction(e)) throw new TypeError("Bind must be called on a function");
        var n = u.call(arguments, 2),
        i = function() {
            return D(e, i, t, this, n.concat(u.call(arguments)))
        };
        return i
    },
    y.partial = function(e) {
        var t = u.call(arguments, 1),
        n = function() {
            for (var i = 0,
            r = t.length,
            o = Array(r), s = 0; r > s; s++) o[s] = t[s] === y ? arguments[i++] : t[s];
            for (; i < arguments.length;) o.push(arguments[i++]);
            return D(e, n, this, this, o)
        };
        return n
    },
    y.bindAll = function(e) {
        var t, n, i = arguments.length;
        if (1 >= i) throw new Error("bindAll must be passed function names");
        for (t = 1; i > t; t++) n = arguments[t],
        e[n] = y.bind(e[n], e);
        return e
    },
    y.memoize = function(e, t) {
        var n = function(i) {
            var r = n.cache,
            o = "" + (t ? t.apply(this, arguments) : i);
            return y.has(r, o) || (r[o] = e.apply(this, arguments)),
            r[o]
        };
        return n.cache = {},
        n
    },
    y.delay = function(e, t) {
        var n = u.call(arguments, 2);
        return setTimeout(function() {
            return e.apply(null, n)
        },
        t)
    },
    y.defer = y.partial(y.delay, y, 1),
    y.throttle = function(e, t, n) {
        var i, r, o, s = null,
        a = 0;
        n || (n = {});
        var l = function() {
            a = n.leading === !1 ? 0 : y.now(),
            s = null,
            o = e.apply(i, r),
            s || (i = r = null)
        };
        return function() {
            var c = y.now();
            a || n.leading !== !1 || (a = c);
            var u = t - (c - a);
            return i = this,
            r = arguments,
            0 >= u || u > t ? (s && (clearTimeout(s), s = null), a = c, o = e.apply(i, r), s || (i = r = null)) : s || n.trailing === !1 || (s = setTimeout(l, u)),
            o
        }
    },
    y.debounce = function(e, t, n) {
        var i, r, o, s, a, l = function() {
            var c = y.now() - s;
            t > c && c >= 0 ? i = setTimeout(l, t - c) : (i = null, n || (a = e.apply(o, r), i || (o = r = null)))
        };
        return function() {
            o = this,
            r = arguments,
            s = y.now();
            var c = n && !i;
            return i || (i = setTimeout(l, t)),
            c && (a = e.apply(o, r), o = r = null),
            a
        }
    },
    y.wrap = function(e, t) {
        return y.partial(t, e)
    },
    y.negate = function(e) {
        return function() {
            return ! e.apply(this, arguments)
        }
    },
    y.compose = function() {
        var e = arguments,
        t = e.length - 1;
        return function() {
            for (var n = t,
            i = e[t].apply(this, arguments); n--;) i = e[n].call(this, i);
            return i
        }
    },
    y.after = function(e, t) {
        return function() {
            return--e < 1 ? t.apply(this, arguments) : void 0
        }
    },
    y.before = function(e, t) {
        var n;
        return function() {
            return--e > 0 && (n = t.apply(this, arguments)),
            1 >= e && (t = null),
            n
        }
    },
    y.once = y.partial(y.before, 2);
    var P = !{
        toString: null
    }.propertyIsEnumerable("toString"),
    A = ["valueOf", "isPrototypeOf", "toString", "propertyIsEnumerable", "hasOwnProperty", "toLocaleString"];
    y.keys = function(e) {
        if (!y.isObject(e)) return [];
        if (h) return h(e);
        var t = [];
        for (var n in e) y.has(e, n) && t.push(n);
        return P && i(e, t),
        t
    },
    y.allKeys = function(e) {
        if (!y.isObject(e)) return [];
        var t = [];
        for (var n in e) t.push(n);
        return P && i(e, t),
        t
    },
    y.values = function(e) {
        for (var t = y.keys(e), n = t.length, i = Array(n), r = 0; n > r; r++) i[r] = e[t[r]];
        return i
    },
    y.mapObject = function(e, t, n) {
        t = x(t, n);
        for (var i, r = y.keys(e), o = r.length, s = {},
        a = 0; o > a; a++) i = r[a],
        s[i] = t(e[i], i, e);
        return s
    },
    y.pairs = function(e) {
        for (var t = y.keys(e), n = t.length, i = Array(n), r = 0; n > r; r++) i[r] = [t[r], e[t[r]]];
        return i
    },
    y.invert = function(e) {
        for (var t = {},
        n = y.keys(e), i = 0, r = n.length; r > i; i++) t[e[n[i]]] = n[i];
        return t
    },
    y.functions = y.methods = function(e) {
        var t = [];
        for (var n in e) y.isFunction(e[n]) && t.push(n);
        return t.sort()
    },
    y.extend = w(y.allKeys),
    y.extendOwn = y.assign = w(y.keys),
    y.findKey = function(e, t, n) {
        t = x(t, n);
        for (var i, r = y.keys(e), o = 0, s = r.length; s > o; o++) if (i = r[o], t(e[i], i, e)) return i
    },
    y.pick = function(e, t, n) {
        var i, r, o = {},
        s = e;
        if (null == s) return o;
        y.isFunction(t) ? (r = y.allKeys(s), i = b(t, n)) : (r = S(arguments, !1, !1, 1), i = function(e, t, n) {
            return t in n
        },
        s = Object(s));
        for (var a = 0,
        l = r.length; l > a; a++) {
            var c = r[a],
            u = s[c];
            i(u, c, s) && (o[c] = u)
        }
        return o
    },
    y.omit = function(e, t, n) {
        if (y.isFunction(t)) t = y.negate(t);
        else {
            var i = y.map(S(arguments, !1, !1, 1), String);
            t = function(e, t) {
                return ! y.contains(i, t)
            }
        }
        return y.pick(e, t, n)
    },
    y.defaults = w(y.allKeys, !0),
    y.create = function(e, t) {
        var n = T(e);
        return t && y.extendOwn(n, t),
        n
    },
    y.clone = function(e) {
        return y.isObject(e) ? y.isArray(e) ? e.slice() : y.extend({},
        e) : e
    },
    y.tap = function(e, t) {
        return t(e),
        e
    },
    y.isMatch = function(e, t) {
        var n = y.keys(t),
        i = n.length;
        if (null == e) return ! i;
        for (var r = Object(e), o = 0; i > o; o++) {
            var s = n[o];
            if (t[s] !== r[s] || !(s in r)) return ! 1
        }
        return ! 0
    };
    var j = function(e, t, n, i) {
        if (e === t) return 0 !== e || 1 / e === 1 / t;
        if (null == e || null == t) return e === t;
        e instanceof y && (e = e._wrapped),
        t instanceof y && (t = t._wrapped);
        var r = f.call(e);
        if (r !== f.call(t)) return ! 1;
        switch (r) {
        case "[object RegExp]":
        case "[object String]":
            return "" + e == "" + t;
        case "[object Number]":
            return + e !== +e ? +t !== +t: 0 === +e ? 1 / +e === 1 / t: +e === +t;
        case "[object Date]":
        case "[object Boolean]":
            return + e === +t
        }
        var o = "[object Array]" === r;
        if (!o) {
            if ("object" != typeof e || "object" != typeof t) return ! 1;
            var s = e.constructor,
            a = t.constructor;
            if (s !== a && !(y.isFunction(s) && s instanceof s && y.isFunction(a) && a instanceof a) && "constructor" in e && "constructor" in t) return ! 1
        }
        n = n || [],
        i = i || [];
        for (var l = n.length; l--;) if (n[l] === e) return i[l] === t;
        if (n.push(e), i.push(t), o) {
            if (l = e.length, l !== t.length) return ! 1;
            for (; l--;) if (!j(e[l], t[l], n, i)) return ! 1
        } else {
            var c, u = y.keys(e);
            if (l = u.length, y.keys(t).length !== l) return ! 1;
            for (; l--;) if (c = u[l], !y.has(t, c) || !j(e[c], t[c], n, i)) return ! 1
        }
        return n.pop(),
        i.pop(),
        !0
    };
    y.isEqual = function(e, t) {
        return j(e, t)
    },
    y.isEmpty = function(e) {
        return null == e ? !0 : E(e) && (y.isArray(e) || y.isString(e) || y.isArguments(e)) ? 0 === e.length: 0 === y.keys(e).length
    },
    y.isElement = function(e) {
        return ! (!e || 1 !== e.nodeType)
    },
    y.isArray = d ||
    function(e) {
        return "[object Array]" === f.call(e)
    },
    y.isObject = function(e) {
        var t = typeof e;
        return "function" === t || "object" === t && !!e
    },
    y.each(["Arguments", "Function", "String", "Number", "Date", "RegExp", "Error"],
    function(e) {
        y["is" + e] = function(t) {
            return f.call(t) === "[object " + e + "]"
        }
    }),
    y.isArguments(arguments) || (y.isArguments = function(e) {
        return y.has(e, "callee")
    }),
    "function" != typeof / . / &&"object" != typeof Int8Array && (y.isFunction = function(e) {
        return "function" == typeof e || !1
    }),
    y.isFinite = function(e) {
        return isFinite(e) && !isNaN(parseFloat(e))
    },
    y.isNaN = function(e) {
        return y.isNumber(e) && e !== +e
    },
    y.isBoolean = function(e) {
        return e === !0 || e === !1 || "[object Boolean]" === f.call(e)
    },
    y.isNull = function(e) {
        return null === e
    },
    y.isUndefined = function(e) {
        return void 0 === e
    },
    y.has = function(e, t) {
        return null != e && p.call(e, t)
    },
    y.noConflict = function() {
        return r._ = o,
        this
    },
    y.identity = function(e) {
        return e
    },
    y.constant = function(e) {
        return function() {
            return e
        }
    },
    y.noop = function() {},
    y.property = _,
    y.propertyOf = function(e) {
        return null == e ?
        function() {}: function(t) {
            return e[t]
        }
    },
    y.matcher = y.matches = function(e) {
        return e = y.extendOwn({},
        e),
        function(t) {
            return y.isMatch(t, e)
        }
    },
    y.times = function(e, t, n) {
        var i = Array(Math.max(0, e));
        t = b(t, n, 1);
        for (var r = 0; e > r; r++) i[r] = t(r);
        return i
    },
    y.random = function(e, t) {
        return null == t && (t = e, e = 0),
        e + Math.floor(Math.random() * (t - e + 1))
    },
    y.now = Date.now ||
    function() {
        return (new Date).getTime()
    };
    var L = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#x27;",
        "`": "&#x60;"
    },
    H = y.invert(L),
    M = function(e) {
        var t = function(t) {
            return e[t]
        },
        n = "(?:" + y.keys(e).join("|") + ")",
        i = RegExp(n),
        r = RegExp(n, "g");
        return function(e) {
            return e = null == e ? "": "" + e,
            i.test(e) ? e.replace(r, t) : e
        }
    };
    y.escape = M(L),
    y.unescape = M(H),
    y.result = function(e, t, n) {
        var i = null == e ? void 0 : e[t];
        return void 0 === i && (i = n),
        y.isFunction(i) ? i.call(e) : i
    };
    var O = 0;
    y.uniqueId = function(e) {
        var t = ++O + "";
        return e ? e + t: t
    },
    y.templateSettings = {
        evaluate: /<%([\s\S]+?)%>/g,
        interpolate: /<%=([\s\S]+?)%>/g,
        escape: /<%-([\s\S]+?)%>/g
    };
    var W = /(.)^/,
    I = {
        "'": "'",
        "\\": "\\",
        "\r": "r",
        "\n": "n",
        "\u2028": "u2028",
        "\u2029": "u2029"
    },
    F = /\\|'|\r|\n|\u2028|\u2029/g,
    q = function(e) {
        return "\\" + I[e]
    };
    y.template = function(e, t, n) { ! t && n && (t = n),
        t = y.defaults({},
        t, y.templateSettings);
        var i = RegExp([(t.escape || W).source, (t.interpolate || W).source, (t.evaluate || W).source].join("|") + "|$", "g"),
        r = 0,
        o = "__p+='";
        e.replace(i,
        function(t, n, i, s, a) {
            return o += e.slice(r, a).replace(F, q),
            r = a + t.length,
            n ? o += "'+\n((__t=(" + n + "))==null?'':_.escape(__t))+\n'": i ? o += "'+\n((__t=(" + i + "))==null?'':__t)+\n'": s && (o += "';\n" + s + "\n__p+='"),
            t
        }),
        o += "';\n",
        t.variable || (o = "with(obj||{}){\n" + o + "}\n"),
        o = "var __t,__p='',__j=Array.prototype.join,print=function(){__p+=__j.call(arguments,'');};\n" + o + "return __p;\n";
        try {
            var s = new Function(t.variable || "obj", "_", o)
        } catch(a) {
            throw a.source = o,
            a
        }
        var l = function(e) {
            return s.call(this, e, y)
        },
        c = t.variable || "obj";
        return l.source = "function(" + c + "){\n" + o + "}",
        l
    },
    y.chain = function(e) {
        var t = y(e);
        return t._chain = !0,
        t
    };
    var B = function(e, t) {
        return e._chain ? y(t).chain() : t
    };
    y.mixin = function(e) {
        y.each(y.functions(e),
        function(t) {
            var n = y[t] = e[t];
            y.prototype[t] = function() {
                var e = [this._wrapped];
                return c.apply(e, arguments),
                B(this, n.apply(y, e))
            }
        })
    },
    y.mixin(y),
    y.each(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"],
    function(e) {
        var t = s[e];
        y.prototype[e] = function() {
            var n = this._wrapped;
            return t.apply(n, arguments),
            "shift" !== e && "splice" !== e || 0 !== n.length || delete n[0],
            B(this, n)
        }
    }),
    y.each(["concat", "join", "slice"],
    function(e) {
        var t = s[e];
        y.prototype[e] = function() {
            return B(this, t.apply(this._wrapped, arguments))
        }
    }),
    y.prototype.value = function() {
        return this._wrapped
    },
    y.prototype.valueOf = y.prototype.toJSON = y.prototype.value,
    y.prototype.toString = function() {
        return "" + this._wrapped
    },
    "function" == typeof define && define.amd && define("underscore", [],
    function() {
        return y
    })
}.call(this),
function(e, t) {
    function n(t, n) {
        var r, o, s, a = t.nodeName.toLowerCase();
        return "area" === a ? (r = t.parentNode, o = r.name, t.href && o && "map" === r.nodeName.toLowerCase() ? (s = e("img[usemap=#" + o + "]")[0], !!s && i(s)) : !1) : (/input|select|textarea|button|object/.test(a) ? !t.disabled: "a" === a ? t.href || n: n) && i(t)
    }
    function i(t) {
        return e.expr.filters.visible(t) && !e(t).parents().addBack().filter(function() {
            return "hidden" === e.css(this, "visibility")
        }).length
    }
    var r = 0,
    o = /^ui-id-\d+$/;
    e.ui = e.ui || {},
    e.extend(e.ui, {
        version: "1.10.4",
        keyCode: {
            BACKSPACE: 8,
            COMMA: 188,
            DELETE: 46,
            DOWN: 40,
            END: 35,
            ENTER: 13,
            ESCAPE: 27,
            HOME: 36,
            LEFT: 37,
            NUMPAD_ADD: 107,
            NUMPAD_DECIMAL: 110,
            NUMPAD_DIVIDE: 111,
            NUMPAD_ENTER: 108,
            NUMPAD_MULTIPLY: 106,
            NUMPAD_SUBTRACT: 109,
            PAGE_DOWN: 34,
            PAGE_UP: 33,
            PERIOD: 190,
            RIGHT: 39,
            SPACE: 32,
            TAB: 9,
            UP: 38
        }
    }),
    e.fn.extend({
        focus: function(t) {
            return function(n, i) {
                return "number" == typeof n ? this.each(function() {
                    var t = this;
                    setTimeout(function() {
                        e(t).focus(),
                        i && i.call(t)
                    },
                    n)
                }) : t.apply(this, arguments)
            }
        } (e.fn.focus),
        scrollParent: function() {
            var t;
            return t = e.ui.ie && /(static|relative)/.test(this.css("position")) || /absolute/.test(this.css("position")) ? this.parents().filter(function() {
                return /(relative|absolute|fixed)/.test(e.css(this, "position")) && /(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"))
            }).eq(0) : this.parents().filter(function() {
                return /(auto|scroll)/.test(e.css(this, "overflow") + e.css(this, "overflow-y") + e.css(this, "overflow-x"))
            }).eq(0),
            /fixed/.test(this.css("position")) || !t.length ? e(document) : t
        },
        zIndex: function(n) {
            if (n !== t) return this.css("zIndex", n);
            if (this.length) for (var i, r, o = e(this[0]); o.length && o[0] !== document;) {
                if (i = o.css("position"), ("absolute" === i || "relative" === i || "fixed" === i) && (r = parseInt(o.css("zIndex"), 10), !isNaN(r) && 0 !== r)) return r;
                o = o.parent()
            }
            return 0
        },
        uniqueId: function() {
            return this.each(function() {
                this.id || (this.id = "ui-id-" + ++r)
            })
        },
        removeUniqueId: function() {
            return this.each(function() {
                o.test(this.id) && e(this).removeAttr("id")
            })
        }
    }),
    e.extend(e.expr[":"], {
        data: e.expr.createPseudo ? e.expr.createPseudo(function(t) {
            return function(n) {
                return !! e.data(n, t)
            }
        }) : function(t, n, i) {
            return !! e.data(t, i[3])
        },
        focusable: function(t) {
            return n(t, !isNaN(e.attr(t, "tabindex")))
        },
        tabbable: function(t) {
            var i = e.attr(t, "tabindex"),
            r = isNaN(i);
            return (r || i >= 0) && n(t, !r)
        }
    }),
    e("<a>").outerWidth(1).jquery || e.each(["Width", "Height"],
    function(n, i) {
        function r(t, n, i, r) {
            return e.each(o,
            function() {
                n -= parseFloat(e.css(t, "padding" + this)) || 0,
                i && (n -= parseFloat(e.css(t, "border" + this + "Width")) || 0),
                r && (n -= parseFloat(e.css(t, "margin" + this)) || 0)
            }),
            n
        }
        var o = "Width" === i ? ["Left", "Right"] : ["Top", "Bottom"],
        s = i.toLowerCase(),
        a = {
            innerWidth: e.fn.innerWidth,
            innerHeight: e.fn.innerHeight,
            outerWidth: e.fn.outerWidth,
            outerHeight: e.fn.outerHeight
        };
        e.fn["inner" + i] = function(n) {
            return n === t ? a["inner" + i].call(this) : this.each(function() {
                e(this).css(s, r(this, n) + "px")
            })
        },
        e.fn["outer" + i] = function(t, n) {
            return "number" != typeof t ? a["outer" + i].call(this, t) : this.each(function() {
                e(this).css(s, r(this, t, !0, n) + "px")
            })
        }
    }),
    e.fn.addBack || (e.fn.addBack = function(e) {
        return this.add(null == e ? this.prevObject: this.prevObject.filter(e))
    }),
    e("<a>").data("a-b", "a").removeData("a-b").data("a-b") && (e.fn.removeData = function(t) {
        return function(n) {
            return arguments.length ? t.call(this, e.camelCase(n)) : t.call(this)
        }
    } (e.fn.removeData)),
    e.ui.ie = !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase()),
    e.support.selectstart = "onselectstart" in document.createElement("div"),
    e.fn.extend({
        disableSelection: function() {
            return this.bind((e.support.selectstart ? "selectstart": "mousedown") + ".ui-disableSelection",
            function(e) {
                e.preventDefault()
            })
        },
        enableSelection: function() {
            return this.unbind(".ui-disableSelection")
        }
    }),
    e.extend(e.ui, {
        plugin: {
            add: function(t, n, i) {
                var r, o = e.ui[t].prototype;
                for (r in i) o.plugins[r] = o.plugins[r] || [],
                o.plugins[r].push([n, i[r]])
            },
            call: function(e, t, n) {
                var i, r = e.plugins[t];
                if (r && e.element[0].parentNode && 11 !== e.element[0].parentNode.nodeType) for (i = 0; r.length > i; i++) e.options[r[i][0]] && r[i][1].apply(e.element, n)
            }
        },
        hasScroll: function(t, n) {
            if ("hidden" === e(t).css("overflow")) return ! 1;
            var i = n && "left" === n ? "scrollLeft": "scrollTop",
            r = !1;
            return t[i] > 0 ? !0 : (t[i] = 1, r = t[i] > 0, t[i] = 0, r)
        }
    })
} (jQuery),
function(e, t) {
    var n = 0,
    i = Array.prototype.slice,
    r = e.cleanData;
    e.cleanData = function(t) {
        for (var n, i = 0; null != (n = t[i]); i++) try {
            e(n).triggerHandler("remove")
        } catch(o) {}
        r(t)
    },
    e.widget = function(n, i, r) {
        var o, s, a, l, c = {},
        u = n.split(".")[0];
        n = n.split(".")[1],
        o = u + "-" + n,
        r || (r = i, i = e.Widget),
        e.expr[":"][o.toLowerCase()] = function(t) {
            return !! e.data(t, o)
        },
        e[u] = e[u] || {},
        s = e[u][n],
        a = e[u][n] = function(e, n) {
            return this._createWidget ? (arguments.length && this._createWidget(e, n), t) : new a(e, n)
        },
        e.extend(a, s, {
            version: r.version,
            _proto: e.extend({},
            r),
            _childConstructors: []
        }),
        l = new i,
        l.options = e.widget.extend({},
        l.options),
        e.each(r,
        function(n, r) {
            return e.isFunction(r) ? (c[n] = function() {
                var e = function() {
                    return i.prototype[n].apply(this, arguments)
                },
                t = function(e) {
                    return i.prototype[n].apply(this, e)
                };
                return function() {
                    var n, i = this._super,
                    o = this._superApply;
                    return this._super = e,
                    this._superApply = t,
                    n = r.apply(this, arguments),
                    this._super = i,
                    this._superApply = o,
                    n
                }
            } (), t) : (c[n] = r, t)
        }),
        a.prototype = e.widget.extend(l, {
            widgetEventPrefix: s ? l.widgetEventPrefix || n: n
        },
        c, {
            constructor: a,
            namespace: u,
            widgetName: n,
            widgetFullName: o
        }),
        s ? (e.each(s._childConstructors,
        function(t, n) {
            var i = n.prototype;
            e.widget(i.namespace + "." + i.widgetName, a, n._proto)
        }), delete s._childConstructors) : i._childConstructors.push(a),
        e.widget.bridge(n, a)
    },
    e.widget.extend = function(n) {
        for (var r, o, s = i.call(arguments, 1), a = 0, l = s.length; l > a; a++) for (r in s[a]) o = s[a][r],
        s[a].hasOwnProperty(r) && o !== t && (n[r] = e.isPlainObject(o) ? e.isPlainObject(n[r]) ? e.widget.extend({},
        n[r], o) : e.widget.extend({},
        o) : o);
        return n
    },
    e.widget.bridge = function(n, r) {
        var o = r.prototype.widgetFullName || n;
        e.fn[n] = function(s) {
            var a = "string" == typeof s,
            l = i.call(arguments, 1),
            c = this;
            return s = !a && l.length ? e.widget.extend.apply(null, [s].concat(l)) : s,
            a ? this.each(function() {
                var i, r = e.data(this, o);
                return r ? e.isFunction(r[s]) && "_" !== s.charAt(0) ? (i = r[s].apply(r, l), i !== r && i !== t ? (c = i && i.jquery ? c.pushStack(i.get()) : i, !1) : t) : e.error("no such method '" + s + "' for " + n + " widget instance") : e.error("cannot call methods on " + n + " prior to initialization; attempted to call method '" + s + "'")
            }) : this.each(function() {
                var t = e.data(this, o);
                t ? t.option(s || {})._init() : e.data(this, o, new r(s, this))
            }),
            c
        }
    },
    e.Widget = function() {},
    e.Widget._childConstructors = [],
    e.Widget.prototype = {
        widgetName: "widget",
        widgetEventPrefix: "",
        defaultElement: "<div>",
        options: {
            disabled: !1,
            create: null
        },
        _createWidget: function(t, i) {
            i = e(i || this.defaultElement || this)[0],
            this.element = e(i),
            this.uuid = n++,
            this.eventNamespace = "." + this.widgetName + this.uuid,
            this.options = e.widget.extend({},
            this.options, this._getCreateOptions(), t),
            this.bindings = e(),
            this.hoverable = e(),
            this.focusable = e(),
            i !== this && (e.data(i, this.widgetFullName, this), this._on(!0, this.element, {
                remove: function(e) {
                    e.target === i && this.destroy()
                }
            }), this.document = e(i.style ? i.ownerDocument: i.document || i), this.window = e(this.document[0].defaultView || this.document[0].parentWindow)),
            this._create(),
            this._trigger("create", null, this._getCreateEventData()),
            this._init()
        },
        _getCreateOptions: e.noop,
        _getCreateEventData: e.noop,
        _create: e.noop,
        _init: e.noop,
        destroy: function() {
            this._destroy(),
            this.element.unbind(this.eventNamespace).removeData(this.widgetName).removeData(this.widgetFullName).removeData(e.camelCase(this.widgetFullName)),
            this.widget().unbind(this.eventNamespace).removeAttr("aria-disabled").removeClass(this.widgetFullName + "-disabled ui-state-disabled"),
            this.bindings.unbind(this.eventNamespace),
            this.hoverable.removeClass("ui-state-hover"),
            this.focusable.removeClass("ui-state-focus")
        },
        _destroy: e.noop,
        widget: function() {
            return this.element
        },
        option: function(n, i) {
            var r, o, s, a = n;
            if (0 === arguments.length) return e.widget.extend({},
            this.options);
            if ("string" == typeof n) if (a = {},
            r = n.split("."), n = r.shift(), r.length) {
                for (o = a[n] = e.widget.extend({},
                this.options[n]), s = 0; r.length - 1 > s; s++) o[r[s]] = o[r[s]] || {},
                o = o[r[s]];
                if (n = r.pop(), 1 === arguments.length) return o[n] === t ? null: o[n];
                o[n] = i
            } else {
                if (1 === arguments.length) return this.options[n] === t ? null: this.options[n];
                a[n] = i
            }
            return this._setOptions(a),
            this
        },
        _setOptions: function(e) {
            var t;
            for (t in e) this._setOption(t, e[t]);
            return this
        },
        _setOption: function(e, t) {
            return this.options[e] = t,
            "disabled" === e && (this.widget().toggleClass(this.widgetFullName + "-disabled ui-state-disabled", !!t).attr("aria-disabled", t), this.hoverable.removeClass("ui-state-hover"), this.focusable.removeClass("ui-state-focus")),
            this
        },
        enable: function() {
            return this._setOption("disabled", !1)
        },
        disable: function() {
            return this._setOption("disabled", !0)
        },
        _on: function(n, i, r) {
            var o, s = this;
            "boolean" != typeof n && (r = i, i = n, n = !1),
            r ? (i = o = e(i), this.bindings = this.bindings.add(i)) : (r = i, i = this.element, o = this.widget()),
            e.each(r,
            function(r, a) {
                function l() {
                    return n || s.options.disabled !== !0 && !e(this).hasClass("ui-state-disabled") ? ("string" == typeof a ? s[a] : a).apply(s, arguments) : t
                }
                "string" != typeof a && (l.guid = a.guid = a.guid || l.guid || e.guid++);
                var c = r.match(/^(\w+)\s*(.*)$/),
                u = c[1] + s.eventNamespace,
                f = c[2];
                f ? o.delegate(f, u, l) : i.bind(u, l)
            })
        },
        _off: function(e, t) {
            t = (t || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace,
            e.unbind(t).undelegate(t)
        },
        _delay: function(e, t) {
            function n() {
                return ("string" == typeof e ? i[e] : e).apply(i, arguments)
            }
            var i = this;
            return setTimeout(n, t || 0)
        },
        _hoverable: function(t) {
            this.hoverable = this.hoverable.add(t),
            this._on(t, {
                mouseenter: function(t) {
                    e(t.currentTarget).addClass("ui-state-hover")
                },
                mouseleave: function(t) {
                    e(t.currentTarget).removeClass("ui-state-hover")
                }
            })
        },
        _focusable: function(t) {
            this.focusable = this.focusable.add(t),
            this._on(t, {
                focusin: function(t) {
                    e(t.currentTarget).addClass("ui-state-focus")
                },
                focusout: function(t) {
                    e(t.currentTarget).removeClass("ui-state-focus")
                }
            })
        },
        _trigger: function(t, n, i) {
            var r, o, s = this.options[t];
            if (i = i || {},
            n = e.Event(n), n.type = (t === this.widgetEventPrefix ? t: this.widgetEventPrefix + t).toLowerCase(), n.target = this.element[0], o = n.originalEvent) for (r in o) r in n || (n[r] = o[r]);
            return this.element.trigger(n, i),
            !(e.isFunction(s) && s.apply(this.element[0], [n].concat(i)) === !1 || n.isDefaultPrevented())
        }
    },
    e.each({
        show: "fadeIn",
        hide: "fadeOut"
    },
    function(t, n) {
        e.Widget.prototype["_" + t] = function(i, r, o) {
            "string" == typeof r && (r = {
                effect: r
            });
            var s, a = r ? r === !0 || "number" == typeof r ? n: r.effect || n: t;
            r = r || {},
            "number" == typeof r && (r = {
                duration: r
            }),
            s = !e.isEmptyObject(r),
            r.complete = o,
            r.delay && i.delay(r.delay),
            s && e.effects && e.effects.effect[a] ? i[t](r) : a !== t && i[a] ? i[a](r.duration, r.easing, o) : i.queue(function(n) {
                e(this)[t](),
                o && o.call(i[0]),
                n()
            })
        }
    })
} (jQuery),
function(e) {
    var t = !1;
    e(document).mouseup(function() {
        t = !1
    }),
    e.widget("ui.mouse", {
        version: "1.10.4",
        options: {
            cancel: "input,textarea,button,select,option",
            distance: 1,
            delay: 0
        },
        _mouseInit: function() {
            var t = this;
            this.element.bind("mousedown." + this.widgetName,
            function(e) {
                return t._mouseDown(e)
            }).bind("click." + this.widgetName,
            function(n) {
                return ! 0 === e.data(n.target, t.widgetName + ".preventClickEvent") ? (e.removeData(n.target, t.widgetName + ".preventClickEvent"), n.stopImmediatePropagation(), !1) : void 0
            }),
            this.started = !1
        },
        _mouseDestroy: function() {
            this.element.unbind("." + this.widgetName),
            this._mouseMoveDelegate && e(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate)
        },
        _mouseDown: function(n) {
            if (!t) {
                this._mouseStarted && this._mouseUp(n),
                this._mouseDownEvent = n;
                var i = this,
                r = 1 === n.which,
                o = "string" == typeof this.options.cancel && n.target.nodeName ? e(n.target).closest(this.options.cancel).length: !1;
                return r && !o && this._mouseCapture(n) ? (this.mouseDelayMet = !this.options.delay, this.mouseDelayMet || (this._mouseDelayTimer = setTimeout(function() {
                    i.mouseDelayMet = !0
                },
                this.options.delay)), this._mouseDistanceMet(n) && this._mouseDelayMet(n) && (this._mouseStarted = this._mouseStart(n) !== !1, !this._mouseStarted) ? (n.preventDefault(), !0) : (!0 === e.data(n.target, this.widgetName + ".preventClickEvent") && e.removeData(n.target, this.widgetName + ".preventClickEvent"), this._mouseMoveDelegate = function(e) {
                    return i._mouseMove(e)
                },
                this._mouseUpDelegate = function(e) {
                    return i._mouseUp(e)
                },
                e(document).bind("mousemove." + this.widgetName, this._mouseMoveDelegate).bind("mouseup." + this.widgetName, this._mouseUpDelegate), n.preventDefault(), t = !0, !0)) : !0
            }
        },
        _mouseMove: function(t) {
            return e.ui.ie && (!document.documentMode || 9 > document.documentMode) && !t.button ? this._mouseUp(t) : this._mouseStarted ? (this._mouseDrag(t), t.preventDefault()) : (this._mouseDistanceMet(t) && this._mouseDelayMet(t) && (this._mouseStarted = this._mouseStart(this._mouseDownEvent, t) !== !1, this._mouseStarted ? this._mouseDrag(t) : this._mouseUp(t)), !this._mouseStarted)
        },
        _mouseUp: function(t) {
            return e(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate),
            this._mouseStarted && (this._mouseStarted = !1, t.target === this._mouseDownEvent.target && e.data(t.target, this.widgetName + ".preventClickEvent", !0), this._mouseStop(t)),
            !1
        },
        _mouseDistanceMet: function(e) {
            return Math.max(Math.abs(this._mouseDownEvent.pageX - e.pageX), Math.abs(this._mouseDownEvent.pageY - e.pageY)) >= this.options.distance
        },
        _mouseDelayMet: function() {
            return this.mouseDelayMet
        },
        _mouseStart: function() {},
        _mouseDrag: function() {},
        _mouseStop: function() {},
        _mouseCapture: function() {
            return ! 0
        }
    })
} (jQuery),
function(e, t) {
    function n(e, t, n) {
        return [parseFloat(e[0]) * (d.test(e[0]) ? t / 100 : 1), parseFloat(e[1]) * (d.test(e[1]) ? n / 100 : 1)]
    }
    function i(t, n) {
        return parseInt(e.css(t, n), 10) || 0
    }
    function r(t) {
        var n = t[0];
        return 9 === n.nodeType ? {
            width: t.width(),
            height: t.height(),
            offset: {
                top: 0,
                left: 0
            }
        }: e.isWindow(n) ? {
            width: t.width(),
            height: t.height(),
            offset: {
                top: t.scrollTop(),
                left: t.scrollLeft()
            }
        }: n.preventDefault ? {
            width: 0,
            height: 0,
            offset: {
                top: n.pageY,
                left: n.pageX
            }
        }: {
            width: t.outerWidth(),
            height: t.outerHeight(),
            offset: t.offset()
        }
    }
    e.ui = e.ui || {};
    var o, s = Math.max,
    a = Math.abs,
    l = Math.round,
    c = /left|center|right/,
    u = /top|center|bottom/,
    f = /[\+\-]\d+(\.[\d]+)?%?/,
    p = /^\w+/,
    d = /%$/,
    h = e.fn.position;
    e.position = {
        scrollbarWidth: function() {
            if (o !== t) return o;
            var n, i, r = e("<div style='display:block;position:absolute;width:50px;height:50px;overflow:hidden;'><div style='height:100px;width:auto;'></div></div>"),
            s = r.children()[0];
            return e("body").append(r),
            n = s.offsetWidth,
            r.css("overflow", "scroll"),
            i = s.offsetWidth,
            n === i && (i = r[0].clientWidth),
            r.remove(),
            o = n - i
        },
        getScrollInfo: function(t) {
            var n = t.isWindow || t.isDocument ? "": t.element.css("overflow-x"),
            i = t.isWindow || t.isDocument ? "": t.element.css("overflow-y"),
            r = "scroll" === n || "auto" === n && t.width < t.element[0].scrollWidth,
            o = "scroll" === i || "auto" === i && t.height < t.element[0].scrollHeight;
            return {
                width: o ? e.position.scrollbarWidth() : 0,
                height: r ? e.position.scrollbarWidth() : 0
            }
        },
        getWithinInfo: function(t) {
            var n = e(t || window),
            i = e.isWindow(n[0]),
            r = !!n[0] && 9 === n[0].nodeType;
            return {
                element: n,
                isWindow: i,
                isDocument: r,
                offset: n.offset() || {
                    left: 0,
                    top: 0
                },
                scrollLeft: n.scrollLeft(),
                scrollTop: n.scrollTop(),
                width: i ? n.width() : n.outerWidth(),
                height: i ? n.height() : n.outerHeight()
            }
        }
    },
    e.fn.position = function(t) {
        if (!t || !t.of) return h.apply(this, arguments);
        t = e.extend({},
        t);
        var o, d, g, m, v, y, b = e(t.of),
        x = e.position.getWithinInfo(t.within),
        w = e.position.getScrollInfo(x),
        T = (t.collision || "flip").split(" "),
        _ = {};
        return y = r(b),
        b[0].preventDefault && (t.at = "left top"),
        d = y.width,
        g = y.height,
        m = y.offset,
        v = e.extend({},
        m),
        e.each(["my", "at"],
        function() {
            var e, n, i = (t[this] || "").split(" ");
            1 === i.length && (i = c.test(i[0]) ? i.concat(["center"]) : u.test(i[0]) ? ["center"].concat(i) : ["center", "center"]),
            i[0] = c.test(i[0]) ? i[0] : "center",
            i[1] = u.test(i[1]) ? i[1] : "center",
            e = f.exec(i[0]),
            n = f.exec(i[1]),
            _[this] = [e ? e[0] : 0, n ? n[0] : 0],
            t[this] = [p.exec(i[0])[0], p.exec(i[1])[0]]
        }),
        1 === T.length && (T[1] = T[0]),
        "right" === t.at[0] ? v.left += d: "center" === t.at[0] && (v.left += d / 2),
        "bottom" === t.at[1] ? v.top += g: "center" === t.at[1] && (v.top += g / 2),
        o = n(_.at, d, g),
        v.left += o[0],
        v.top += o[1],
        this.each(function() {
            var r, c, u = e(this),
            f = u.outerWidth(),
            p = u.outerHeight(),
            h = i(this, "marginLeft"),
            y = i(this, "marginTop"),
            C = f + h + i(this, "marginRight") + w.width,
            N = p + y + i(this, "marginBottom") + w.height,
            E = e.extend({},
            v),
            k = n(_.my, u.outerWidth(), u.outerHeight());
            "right" === t.my[0] ? E.left -= f: "center" === t.my[0] && (E.left -= f / 2),
            "bottom" === t.my[1] ? E.top -= p: "center" === t.my[1] && (E.top -= p / 2),
            E.left += k[0],
            E.top += k[1],
            e.support.offsetFractions || (E.left = l(E.left), E.top = l(E.top)),
            r = {
                marginLeft: h,
                marginTop: y
            },
            e.each(["left", "top"],
            function(n, i) {
                e.ui.position[T[n]] && e.ui.position[T[n]][i](E, {
                    targetWidth: d,
                    targetHeight: g,
                    elemWidth: f,
                    elemHeight: p,
                    collisionPosition: r,
                    collisionWidth: C,
                    collisionHeight: N,
                    offset: [o[0] + k[0], o[1] + k[1]],
                    my: t.my,
                    at: t.at,
                    within: x,
                    elem: u
                })
            }),
            t.using && (c = function(e) {
                var n = m.left - E.left,
                i = n + d - f,
                r = m.top - E.top,
                o = r + g - p,
                l = {
                    target: {
                        element: b,
                        left: m.left,
                        top: m.top,
                        width: d,
                        height: g
                    },
                    element: {
                        element: u,
                        left: E.left,
                        top: E.top,
                        width: f,
                        height: p
                    },
                    horizontal: 0 > i ? "left": n > 0 ? "right": "center",
                    vertical: 0 > o ? "top": r > 0 ? "bottom": "middle"
                };
                f > d && d > a(n + i) && (l.horizontal = "center"),
                p > g && g > a(r + o) && (l.vertical = "middle"),
                l.important = s(a(n), a(i)) > s(a(r), a(o)) ? "horizontal": "vertical",
                t.using.call(this, e, l)
            }),
            u.offset(e.extend(E, {
                using: c
            }))
        })
    },
    e.ui.position = {
        fit: {
            left: function(e, t) {
                var n, i = t.within,
                r = i.isWindow ? i.scrollLeft: i.offset.left,
                o = i.width,
                a = e.left - t.collisionPosition.marginLeft,
                l = r - a,
                c = a + t.collisionWidth - o - r;
                t.collisionWidth > o ? l > 0 && 0 >= c ? (n = e.left + l + t.collisionWidth - o - r, e.left += l - n) : e.left = c > 0 && 0 >= l ? r: l > c ? r + o - t.collisionWidth: r: l > 0 ? e.left += l: c > 0 ? e.left -= c: e.left = s(e.left - a, e.left)
            },
            top: function(e, t) {
                var n, i = t.within,
                r = i.isWindow ? i.scrollTop: i.offset.top,
                o = t.within.height,
                a = e.top - t.collisionPosition.marginTop,
                l = r - a,
                c = a + t.collisionHeight - o - r;
                t.collisionHeight > o ? l > 0 && 0 >= c ? (n = e.top + l + t.collisionHeight - o - r, e.top += l - n) : e.top = c > 0 && 0 >= l ? r: l > c ? r + o - t.collisionHeight: r: l > 0 ? e.top += l: c > 0 ? e.top -= c: e.top = s(e.top - a, e.top)
            }
        },
        flip: {
            left: function(e, t) {
                var n, i, r = t.within,
                o = r.offset.left + r.scrollLeft,
                s = r.width,
                l = r.isWindow ? r.scrollLeft: r.offset.left,
                c = e.left - t.collisionPosition.marginLeft,
                u = c - l,
                f = c + t.collisionWidth - s - l,
                p = "left" === t.my[0] ? -t.elemWidth: "right" === t.my[0] ? t.elemWidth: 0,
                d = "left" === t.at[0] ? t.targetWidth: "right" === t.at[0] ? -t.targetWidth: 0,
                h = -2 * t.offset[0];
                0 > u ? (n = e.left + p + d + h + t.collisionWidth - s - o, (0 > n || a(u) > n) && (e.left += p + d + h)) : f > 0 && (i = e.left - t.collisionPosition.marginLeft + p + d + h - l, (i > 0 || f > a(i)) && (e.left += p + d + h))
            },
            top: function(e, t) {
                var n, i, r = t.within,
                o = r.offset.top + r.scrollTop,
                s = r.height,
                l = r.isWindow ? r.scrollTop: r.offset.top,
                c = e.top - t.collisionPosition.marginTop,
                u = c - l,
                f = c + t.collisionHeight - s - l,
                p = "top" === t.my[1],
                d = p ? -t.elemHeight: "bottom" === t.my[1] ? t.elemHeight: 0,
                h = "top" === t.at[1] ? t.targetHeight: "bottom" === t.at[1] ? -t.targetHeight: 0,
                g = -2 * t.offset[1];
                0 > u ? (i = e.top + d + h + g + t.collisionHeight - s - o, e.top + d + h + g > u && (0 > i || a(u) > i) && (e.top += d + h + g)) : f > 0 && (n = e.top - t.collisionPosition.marginTop + d + h + g - l, e.top + d + h + g > f && (n > 0 || f > a(n)) && (e.top += d + h + g))
            }
        },
        flipfit: {
            left: function() {
                e.ui.position.flip.left.apply(this, arguments),
                e.ui.position.fit.left.apply(this, arguments)
            },
            top: function() {
                e.ui.position.flip.top.apply(this, arguments),
                e.ui.position.fit.top.apply(this, arguments)
            }
        }
    },
    function() {
        var t, n, i, r, o, s = document.getElementsByTagName("body")[0],
        a = document.createElement("div");
        t = document.createElement(s ? "div": "body"),
        i = {
            visibility: "hidden",
            width: 0,
            height: 0,
            border: 0,
            margin: 0,
            background: "none"
        },
        s && e.extend(i, {
            position: "absolute",
            left: "-1000px",
            top: "-1000px"
        });
        for (o in i) t.style[o] = i[o];
        t.appendChild(a),
        n = s || document.documentElement,
        n.insertBefore(t, n.firstChild),
        a.style.cssText = "position: absolute; left: 10.7432222px;",
        r = e(a).offset().left,
        e.support.offsetFractions = r > 10 && 11 > r,
        t.innerHTML = "",
        n.removeChild(t)
    } ()
} (jQuery),
function(e) {
    e.widget("ui.draggable", e.ui.mouse, {
        version: "1.10.4",
        widgetEventPrefix: "drag",
        options: {
            addClasses: !0,
            appendTo: "parent",
            axis: !1,
            connectToSortable: !1,
            containment: !1,
            cursor: "auto",
            cursorAt: !1,
            grid: !1,
            handle: !1,
            helper: "original",
            iframeFix: !1,
            opacity: !1,
            refreshPositions: !1,
            revert: !1,
            revertDuration: 500,
            scope: "default",
            scroll: !0,
            scrollSensitivity: 20,
            scrollSpeed: 20,
            snap: !1,
            snapMode: "both",
            snapTolerance: 20,
            stack: !1,
            zIndex: !1,
            drag: null,
            start: null,
            stop: null
        },
        _create: function() {
            "original" !== this.options.helper || /^(?:r|a|f)/.test(this.element.css("position")) || (this.element[0].style.position = "relative"),
            this.options.addClasses && this.element.addClass("ui-draggable"),
            this.options.disabled && this.element.addClass("ui-draggable-disabled"),
            this._mouseInit()
        },
        _destroy: function() {
            this.element.removeClass("ui-draggable ui-draggable-dragging ui-draggable-disabled"),
            this._mouseDestroy()
        },
        _mouseCapture: function(t) {
            var n = this.options;
            return this.helper || n.disabled || e(t.target).closest(".ui-resizable-handle").length > 0 ? !1 : (this.handle = this._getHandle(t), this.handle ? (e(n.iframeFix === !0 ? "iframe": n.iframeFix).each(function() {
                e("<div class='ui-draggable-iframeFix' style='background: #fff;'></div>").css({
                    width: this.offsetWidth + "px",
                    height: this.offsetHeight + "px",
                    position: "absolute",
                    opacity: "0.001",
                    zIndex: 1e3
                }).css(e(this).offset()).appendTo("body")
            }), !0) : !1)
        },
        _mouseStart: function(t) {
            var n = this.options;
            return this.helper = this._createHelper(t),
            this.helper.addClass("ui-draggable-dragging"),
            this._cacheHelperProportions(),
            e.ui.ddmanager && (e.ui.ddmanager.current = this),
            this._cacheMargins(),
            this.cssPosition = this.helper.css("position"),
            this.scrollParent = this.helper.scrollParent(),
            this.offsetParent = this.helper.offsetParent(),
            this.offsetParentCssPosition = this.offsetParent.css("position"),
            this.offset = this.positionAbs = this.element.offset(),
            this.offset = {
                top: this.offset.top - this.margins.top,
                left: this.offset.left - this.margins.left
            },
            this.offset.scroll = !1,
            e.extend(this.offset, {
                click: {
                    left: t.pageX - this.offset.left,
                    top: t.pageY - this.offset.top
                },
                parent: this._getParentOffset(),
                relative: this._getRelativeOffset()
            }),
            this.originalPosition = this.position = this._generatePosition(t),
            this.originalPageX = t.pageX,
            this.originalPageY = t.pageY,
            n.cursorAt && this._adjustOffsetFromHelper(n.cursorAt),
            this._setContainment(),
            this._trigger("start", t) === !1 ? (this._clear(), !1) : (this._cacheHelperProportions(), e.ui.ddmanager && !n.dropBehaviour && e.ui.ddmanager.prepareOffsets(this, t), this._mouseDrag(t, !0), e.ui.ddmanager && e.ui.ddmanager.dragStart(this, t), !0)
        },
        _mouseDrag: function(t, n) {
            if ("fixed" === this.offsetParentCssPosition && (this.offset.parent = this._getParentOffset()), this.position = this._generatePosition(t), this.positionAbs = this._convertPositionTo("absolute"), !n) {
                var i = this._uiHash();
                if (this._trigger("drag", t, i) === !1) return this._mouseUp({}),
                !1;
                this.position = i.position
            }
            return this.options.axis && "y" === this.options.axis || (this.helper[0].style.left = this.position.left + "px"),
            this.options.axis && "x" === this.options.axis || (this.helper[0].style.top = this.position.top + "px"),
            e.ui.ddmanager && e.ui.ddmanager.drag(this, t),
            !1
        },
        _mouseStop: function(t) {
            var n = this,
            i = !1;
            return e.ui.ddmanager && !this.options.dropBehaviour && (i = e.ui.ddmanager.drop(this, t)),
            this.dropped && (i = this.dropped, this.dropped = !1),
            "original" !== this.options.helper || e.contains(this.element[0].ownerDocument, this.element[0]) ? ("invalid" === this.options.revert && !i || "valid" === this.options.revert && i || this.options.revert === !0 || e.isFunction(this.options.revert) && this.options.revert.call(this.element, i) ? e(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10),
            function() {
                n._trigger("stop", t) !== !1 && n._clear()
            }) : this._trigger("stop", t) !== !1 && this._clear(), !1) : !1
        },
        _mouseUp: function(t) {
            return e("div.ui-draggable-iframeFix").each(function() {
                this.parentNode.removeChild(this)
            }),
            e.ui.ddmanager && e.ui.ddmanager.dragStop(this, t),
            e.ui.mouse.prototype._mouseUp.call(this, t)
        },
        cancel: function() {
            return this.helper.is(".ui-draggable-dragging") ? this._mouseUp({}) : this._clear(),
            this
        },
        _getHandle: function(t) {
            return this.options.handle ? !!e(t.target).closest(this.element.find(this.options.handle)).length: !0
        },
        _createHelper: function(t) {
            var n = this.options,
            i = e.isFunction(n.helper) ? e(n.helper.apply(this.element[0], [t])) : "clone" === n.helper ? this.element.clone().removeAttr("id") : this.element;
            return i.parents("body").length || i.appendTo("parent" === n.appendTo ? this.element[0].parentNode: n.appendTo),
            i[0] === this.element[0] || /(fixed|absolute)/.test(i.css("position")) || i.css("position", "absolute"),
            i
        },
        _adjustOffsetFromHelper: function(t) {
            "string" == typeof t && (t = t.split(" ")),
            e.isArray(t) && (t = {
                left: +t[0],
                top: +t[1] || 0
            }),
            "left" in t && (this.offset.click.left = t.left + this.margins.left),
            "right" in t && (this.offset.click.left = this.helperProportions.width - t.right + this.margins.left),
            "top" in t && (this.offset.click.top = t.top + this.margins.top),
            "bottom" in t && (this.offset.click.top = this.helperProportions.height - t.bottom + this.margins.top)
        },
        _getParentOffset: function() {
            var t = this.offsetParent.offset();
            return "absolute" === this.cssPosition && this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) && (t.left += this.scrollParent.scrollLeft(), t.top += this.scrollParent.scrollTop()),
            (this.offsetParent[0] === document.body || this.offsetParent[0].tagName && "html" === this.offsetParent[0].tagName.toLowerCase() && e.ui.ie) && (t = {
                top: 0,
                left: 0
            }),
            {
                top: t.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
                left: t.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
            }
        },
        _getRelativeOffset: function() {
            if ("relative" === this.cssPosition) {
                var e = this.element.position();
                return {
                    top: e.top - (parseInt(this.helper.css("top"), 10) || 0) + this.scrollParent.scrollTop(),
                    left: e.left - (parseInt(this.helper.css("left"), 10) || 0) + this.scrollParent.scrollLeft()
                }
            }
            return {
                top: 0,
                left: 0
            }
        },
        _cacheMargins: function() {
            this.margins = {
                left: parseInt(this.element.css("marginLeft"), 10) || 0,
                top: parseInt(this.element.css("marginTop"), 10) || 0,
                right: parseInt(this.element.css("marginRight"), 10) || 0,
                bottom: parseInt(this.element.css("marginBottom"), 10) || 0
            }
        },
        _cacheHelperProportions: function() {
            this.helperProportions = {
                width: this.helper.outerWidth(),
                height: this.helper.outerHeight()
            }
        },
        _setContainment: function() {
            var t, n, i, r = this.options;
            return r.containment ? "window" === r.containment ? void(this.containment = [e(window).scrollLeft() - this.offset.relative.left - this.offset.parent.left, e(window).scrollTop() - this.offset.relative.top - this.offset.parent.top, e(window).scrollLeft() + e(window).width() - this.helperProportions.width - this.margins.left, e(window).scrollTop() + (e(window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top]) : "document" === r.containment ? void(this.containment = [0, 0, e(document).width() - this.helperProportions.width - this.margins.left, (e(document).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top]) : r.containment.constructor === Array ? void(this.containment = r.containment) : ("parent" === r.containment && (r.containment = this.helper[0].parentNode), n = e(r.containment), i = n[0], void(i && (t = "hidden" !== n.css("overflow"), this.containment = [(parseInt(n.css("borderLeftWidth"), 10) || 0) + (parseInt(n.css("paddingLeft"), 10) || 0), (parseInt(n.css("borderTopWidth"), 10) || 0) + (parseInt(n.css("paddingTop"), 10) || 0), (t ? Math.max(i.scrollWidth, i.offsetWidth) : i.offsetWidth) - (parseInt(n.css("borderRightWidth"), 10) || 0) - (parseInt(n.css("paddingRight"), 10) || 0) - this.helperProportions.width - this.margins.left - this.margins.right, (t ? Math.max(i.scrollHeight, i.offsetHeight) : i.offsetHeight) - (parseInt(n.css("borderBottomWidth"), 10) || 0) - (parseInt(n.css("paddingBottom"), 10) || 0) - this.helperProportions.height - this.margins.top - this.margins.bottom], this.relative_container = n))) : void(this.containment = null)
        },
        _convertPositionTo: function(t, n) {
            n || (n = this.position);
            var i = "absolute" === t ? 1 : -1,
            r = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent: this.offsetParent;
            return this.offset.scroll || (this.offset.scroll = {
                top: r.scrollTop(),
                left: r.scrollLeft()
            }),
            {
                top: n.top + this.offset.relative.top * i + this.offset.parent.top * i - ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : this.offset.scroll.top) * i,
                left: n.left + this.offset.relative.left * i + this.offset.parent.left * i - ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : this.offset.scroll.left) * i
            }
        },
        _generatePosition: function(t) {
            var n, i, r, o, s = this.options,
            a = "absolute" !== this.cssPosition || this.scrollParent[0] !== document && e.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent: this.offsetParent,
            l = t.pageX,
            c = t.pageY;
            return this.offset.scroll || (this.offset.scroll = {
                top: a.scrollTop(),
                left: a.scrollLeft()
            }),
            this.originalPosition && (this.containment && (this.relative_container ? (i = this.relative_container.offset(), n = [this.containment[0] + i.left, this.containment[1] + i.top, this.containment[2] + i.left, this.containment[3] + i.top]) : n = this.containment, t.pageX - this.offset.click.left < n[0] && (l = n[0] + this.offset.click.left), t.pageY - this.offset.click.top < n[1] && (c = n[1] + this.offset.click.top), t.pageX - this.offset.click.left > n[2] && (l = n[2] + this.offset.click.left), t.pageY - this.offset.click.top > n[3] && (c = n[3] + this.offset.click.top)), s.grid && (r = s.grid[1] ? this.originalPageY + Math.round((c - this.originalPageY) / s.grid[1]) * s.grid[1] : this.originalPageY, c = n ? r - this.offset.click.top >= n[1] || r - this.offset.click.top > n[3] ? r: r - this.offset.click.top >= n[1] ? r - s.grid[1] : r + s.grid[1] : r, o = s.grid[0] ? this.originalPageX + Math.round((l - this.originalPageX) / s.grid[0]) * s.grid[0] : this.originalPageX, l = n ? o - this.offset.click.left >= n[0] || o - this.offset.click.left > n[2] ? o: o - this.offset.click.left >= n[0] ? o - s.grid[0] : o + s.grid[0] : o)),
            {
                top: c - this.offset.click.top - this.offset.relative.top - this.offset.parent.top + ("fixed" === this.cssPosition ? -this.scrollParent.scrollTop() : this.offset.scroll.top),
                left: l - this.offset.click.left - this.offset.relative.left - this.offset.parent.left + ("fixed" === this.cssPosition ? -this.scrollParent.scrollLeft() : this.offset.scroll.left)
            }
        },
        _clear: function() {
            this.helper.removeClass("ui-draggable-dragging"),
            this.helper[0] === this.element[0] || this.cancelHelperRemoval || this.helper.remove(),
            this.helper = null,
            this.cancelHelperRemoval = !1
        },
        _trigger: function(t, n, i) {
            return i = i || this._uiHash(),
            e.ui.plugin.call(this, t, [n, i]),
            "drag" === t && (this.positionAbs = this._convertPositionTo("absolute")),
            e.Widget.prototype._trigger.call(this, t, n, i)
        },
        plugins: {},
        _uiHash: function() {
            return {
                helper: this.helper,
                position: this.position,
                originalPosition: this.originalPosition,
                offset: this.positionAbs
            }
        }
    }),
    e.ui.plugin.add("draggable", "connectToSortable", {
        start: function(t, n) {
            var i = e(this).data("ui-draggable"),
            r = i.options,
            o = e.extend({},
            n, {
                item: i.element
            });
            i.sortables = [],
            e(r.connectToSortable).each(function() {
                var n = e.data(this, "ui-sortable");
                n && !n.options.disabled && (i.sortables.push({
                    instance: n,
                    shouldRevert: n.options.revert
                }), n.refreshPositions(), n._trigger("activate", t, o))
            })
        },
        stop: function(t, n) {
            var i = e(this).data("ui-draggable"),
            r = e.extend({},
            n, {
                item: i.element
            });
            e.each(i.sortables,
            function() {
                this.instance.isOver ? (this.instance.isOver = 0, i.cancelHelperRemoval = !0, this.instance.cancelHelperRemoval = !1, this.shouldRevert && (this.instance.options.revert = this.shouldRevert), this.instance._mouseStop(t), this.instance.options.helper = this.instance.options._helper, "original" === i.options.helper && this.instance.currentItem.css({
                    top: "auto",
                    left: "auto"
                })) : (this.instance.cancelHelperRemoval = !1, this.instance._trigger("deactivate", t, r))
            })
        },
        drag: function(t, n) {
            var i = e(this).data("ui-draggable"),
            r = this;
            e.each(i.sortables,
            function() {
                var o = !1,
                s = this;
                this.instance.positionAbs = i.positionAbs,
                this.instance.helperProportions = i.helperProportions,
                this.instance.offset.click = i.offset.click,
                this.instance._intersectsWith(this.instance.containerCache) && (o = !0, e.each(i.sortables,
                function() {
                    return this.instance.positionAbs = i.positionAbs,
                    this.instance.helperProportions = i.helperProportions,
                    this.instance.offset.click = i.offset.click,
                    this !== s && this.instance._intersectsWith(this.instance.containerCache) && e.contains(s.instance.element[0], this.instance.element[0]) && (o = !1),
                    o
                })),
                o ? (this.instance.isOver || (this.instance.isOver = 1, this.instance.currentItem = e(r).clone().removeAttr("id").appendTo(this.instance.element).data("ui-sortable-item", !0), this.instance.options._helper = this.instance.options.helper, this.instance.options.helper = function() {
                    return n.helper[0]
                },
                t.target = this.instance.currentItem[0], this.instance._mouseCapture(t, !0), this.instance._mouseStart(t, !0, !0), this.instance.offset.click.top = i.offset.click.top, this.instance.offset.click.left = i.offset.click.left, this.instance.offset.parent.left -= i.offset.parent.left - this.instance.offset.parent.left, this.instance.offset.parent.top -= i.offset.parent.top - this.instance.offset.parent.top, i._trigger("toSortable", t), i.dropped = this.instance.element, i.currentItem = i.element, this.instance.fromOutside = i), this.instance.currentItem && this.instance._mouseDrag(t)) : this.instance.isOver && (this.instance.isOver = 0, this.instance.cancelHelperRemoval = !0, this.instance.options.revert = !1, this.instance._trigger("out", t, this.instance._uiHash(this.instance)), this.instance._mouseStop(t, !0), this.instance.options.helper = this.instance.options._helper, this.instance.currentItem.remove(), this.instance.placeholder && this.instance.placeholder.remove(), i._trigger("fromSortable", t), i.dropped = !1)
            })
        }
    }),
    e.ui.plugin.add("draggable", "cursor", {
        start: function() {
            var t = e("body"),
            n = e(this).data("ui-draggable").options;
            t.css("cursor") && (n._cursor = t.css("cursor")),
            t.css("cursor", n.cursor)
        },
        stop: function() {
            var t = e(this).data("ui-draggable").options;
            t._cursor && e("body").css("cursor", t._cursor)
        }
    }),
    e.ui.plugin.add("draggable", "opacity", {
        start: function(t, n) {
            var i = e(n.helper),
            r = e(this).data("ui-draggable").options;
            i.css("opacity") && (r._opacity = i.css("opacity")),
            i.css("opacity", r.opacity)
        },
        stop: function(t, n) {
            var i = e(this).data("ui-draggable").options;
            i._opacity && e(n.helper).css("opacity", i._opacity)
        }
    }),
    e.ui.plugin.add("draggable", "scroll", {
        start: function() {
            var t = e(this).data("ui-draggable");
            t.scrollParent[0] !== document && "HTML" !== t.scrollParent[0].tagName && (t.overflowOffset = t.scrollParent.offset())
        },
        drag: function(t) {
            var n = e(this).data("ui-draggable"),
            i = n.options,
            r = !1;
            n.scrollParent[0] !== document && "HTML" !== n.scrollParent[0].tagName ? (i.axis && "x" === i.axis || (n.overflowOffset.top + n.scrollParent[0].offsetHeight - t.pageY < i.scrollSensitivity ? n.scrollParent[0].scrollTop = r = n.scrollParent[0].scrollTop + i.scrollSpeed: t.pageY - n.overflowOffset.top < i.scrollSensitivity && (n.scrollParent[0].scrollTop = r = n.scrollParent[0].scrollTop - i.scrollSpeed)), i.axis && "y" === i.axis || (n.overflowOffset.left + n.scrollParent[0].offsetWidth - t.pageX < i.scrollSensitivity ? n.scrollParent[0].scrollLeft = r = n.scrollParent[0].scrollLeft + i.scrollSpeed: t.pageX - n.overflowOffset.left < i.scrollSensitivity && (n.scrollParent[0].scrollLeft = r = n.scrollParent[0].scrollLeft - i.scrollSpeed))) : (i.axis && "x" === i.axis || (t.pageY - e(document).scrollTop() < i.scrollSensitivity ? r = e(document).scrollTop(e(document).scrollTop() - i.scrollSpeed) : e(window).height() - (t.pageY - e(document).scrollTop()) < i.scrollSensitivity && (r = e(document).scrollTop(e(document).scrollTop() + i.scrollSpeed))), i.axis && "y" === i.axis || (t.pageX - e(document).scrollLeft() < i.scrollSensitivity ? r = e(document).scrollLeft(e(document).scrollLeft() - i.scrollSpeed) : e(window).width() - (t.pageX - e(document).scrollLeft()) < i.scrollSensitivity && (r = e(document).scrollLeft(e(document).scrollLeft() + i.scrollSpeed)))),
            r !== !1 && e.ui.ddmanager && !i.dropBehaviour && e.ui.ddmanager.prepareOffsets(n, t)
        }
    }),
    e.ui.plugin.add("draggable", "snap", {
        start: function() {
            var t = e(this).data("ui-draggable"),
            n = t.options;
            t.snapElements = [],
            e(n.snap.constructor !== String ? n.snap.items || ":data(ui-draggable)": n.snap).each(function() {
                var n = e(this),
                i = n.offset();
                this !== t.element[0] && t.snapElements.push({
                    item: this,
                    width: n.outerWidth(),
                    height: n.outerHeight(),
                    top: i.top,
                    left: i.left
                })
            })
        },
        drag: function(t, n) {
            var i, r, o, s, a, l, c, u, f, p, d = e(this).data("ui-draggable"),
            h = d.options,
            g = h.snapTolerance,
            m = n.offset.left,
            v = m + d.helperProportions.width,
            y = n.offset.top,
            b = y + d.helperProportions.height;
            for (f = d.snapElements.length - 1; f >= 0; f--) a = d.snapElements[f].left,
            l = a + d.snapElements[f].width,
            c = d.snapElements[f].top,
            u = c + d.snapElements[f].height,
            a - g > v || m > l + g || c - g > b || y > u + g || !e.contains(d.snapElements[f].item.ownerDocument, d.snapElements[f].item) ? (d.snapElements[f].snapping && d.options.snap.release && d.options.snap.release.call(d.element, t, e.extend(d._uiHash(), {
                snapItem: d.snapElements[f].item
            })), d.snapElements[f].snapping = !1) : ("inner" !== h.snapMode && (i = g >= Math.abs(c - b), r = g >= Math.abs(u - y), o = g >= Math.abs(a - v), s = g >= Math.abs(l - m), i && (n.position.top = d._convertPositionTo("relative", {
                top: c - d.helperProportions.height,
                left: 0
            }).top - d.margins.top), r && (n.position.top = d._convertPositionTo("relative", {
                top: u,
                left: 0
            }).top - d.margins.top), o && (n.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: a - d.helperProportions.width
            }).left - d.margins.left), s && (n.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: l
            }).left - d.margins.left)), p = i || r || o || s, "outer" !== h.snapMode && (i = g >= Math.abs(c - y), r = g >= Math.abs(u - b), o = g >= Math.abs(a - m), s = g >= Math.abs(l - v), i && (n.position.top = d._convertPositionTo("relative", {
                top: c,
                left: 0
            }).top - d.margins.top), r && (n.position.top = d._convertPositionTo("relative", {
                top: u - d.helperProportions.height,
                left: 0
            }).top - d.margins.top), o && (n.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: a
            }).left - d.margins.left), s && (n.position.left = d._convertPositionTo("relative", {
                top: 0,
                left: l - d.helperProportions.width
            }).left - d.margins.left)), !d.snapElements[f].snapping && (i || r || o || s || p) && d.options.snap.snap && d.options.snap.snap.call(d.element, t, e.extend(d._uiHash(), {
                snapItem: d.snapElements[f].item
            })), d.snapElements[f].snapping = i || r || o || s || p)
        }
    }),
    e.ui.plugin.add("draggable", "stack", {
        start: function() {
            var t, n = this.data("ui-draggable").options,
            i = e.makeArray(e(n.stack)).sort(function(t, n) {
                return (parseInt(e(t).css("zIndex"), 10) || 0) - (parseInt(e(n).css("zIndex"), 10) || 0)
            });
            i.length && (t = parseInt(e(i[0]).css("zIndex"), 10) || 0, e(i).each(function(n) {
                e(this).css("zIndex", t + n)
            }), this.css("zIndex", t + i.length))
        }
    }),
    e.ui.plugin.add("draggable", "zIndex", {
        start: function(t, n) {
            var i = e(n.helper),
            r = e(this).data("ui-draggable").options;
            i.css("zIndex") && (r._zIndex = i.css("zIndex")),
            i.css("zIndex", r.zIndex)
        },
        stop: function(t, n) {
            var i = e(this).data("ui-draggable").options;
            i._zIndex && e(n.helper).css("zIndex", i._zIndex)
        }
    })
} (jQuery),
!
function(e) {
    "function" == typeof define && define.amd ? define(["jquery"], e) : "object" == typeof exports ? module.exports = e: e(jQuery)
} (function(e) {
    function t(t) {
        var s = t || window.event,
        a = l.call(arguments, 1),
        c = 0,
        f = 0,
        p = 0,
        d = 0,
        h = 0,
        g = 0;
        if (t = e.event.fix(s), t.type = "mousewheel", "detail" in s && (p = -1 * s.detail), "wheelDelta" in s && (p = s.wheelDelta), "wheelDeltaY" in s && (p = s.wheelDeltaY), "wheelDeltaX" in s && (f = -1 * s.wheelDeltaX), "axis" in s && s.axis === s.HORIZONTAL_AXIS && (f = -1 * p, p = 0), c = 0 === p ? f: p, "deltaY" in s && (p = -1 * s.deltaY, c = p), "deltaX" in s && (f = s.deltaX, 0 === p && (c = -1 * f)), 0 !== p || 0 !== f) {
            if (1 === s.deltaMode) {
                var m = e.data(this, "mousewheel-line-height");
                c *= m,
                p *= m,
                f *= m
            } else if (2 === s.deltaMode) {
                var v = e.data(this, "mousewheel-page-height");
                c *= v,
                p *= v,
                f *= v
            }
            if (d = Math.max(Math.abs(p), Math.abs(f)), (!o || o > d) && (o = d, i(s, d) && (o /= 40)), i(s, d) && (c /= 40, f /= 40, p /= 40), c = Math[c >= 1 ? "floor": "ceil"](c / o), f = Math[f >= 1 ? "floor": "ceil"](f / o), p = Math[p >= 1 ? "floor": "ceil"](p / o), u.settings.normalizeOffset && this.getBoundingClientRect) {
                var y = this.getBoundingClientRect();
                h = t.clientX - y.left,
                g = t.clientY - y.top
            }
            return t.deltaX = f,
            t.deltaY = p,
            t.deltaFactor = o,
            t.offsetX = h,
            t.offsetY = g,
            t.deltaMode = 0,
            a.unshift(t, c, f, p),
            r && clearTimeout(r),
            r = setTimeout(n, 200),
            (e.event.dispatch || e.event.handle).apply(this, a)
        }
    }
    function n() {
        o = null
    }
    function i(e, t) {
        return u.settings.adjustOldDeltas && "mousewheel" === e.type && t % 120 === 0
    }
    var r, o, s = ["wheel", "mousewheel", "DOMMouseScroll", "MozMousePixelScroll"],
    a = "onwheel" in document || document.documentMode >= 9 ? ["wheel"] : ["mousewheel", "DomMouseScroll", "MozMousePixelScroll"],
    l = Array.prototype.slice;
    if (e.event.fixHooks) for (var c = s.length; c;) e.event.fixHooks[s[--c]] = e.event.mouseHooks;
    var u = e.event.special.mousewheel = {
        version: "3.1.11",
        setup: function() {
            if (this.addEventListener) for (var n = a.length; n;) this.addEventListener(a[--n], t, !1);
            else this.onmousewheel = t;
            e.data(this, "mousewheel-line-height", u.getLineHeight(this)),
            e.data(this, "mousewheel-page-height", u.getPageHeight(this))
        },
        teardown: function() {
            if (this.removeEventListener) for (var n = a.length; n;) this.removeEventListener(a[--n], t, !1);
            else this.onmousewheel = null;
            e.removeData(this, "mousewheel-line-height"),
            e.removeData(this, "mousewheel-page-height")
        },
        getLineHeight: function(t) {
            var n = e(t)["offsetParent" in e.fn ? "offsetParent": "parent"]();
            return n.length || (n = e("body")),
            parseInt(n.css("fontSize"), 10)
        },
        getPageHeight: function(t) {
            return e(t).height()
        },
        settings: {
            adjustOldDeltas: !0,
            normalizeOffset: !0
        }
    };
    e.fn.extend({
        mousewheel: function(e) {
            return e ? this.bind("mousewheel", e) : this.trigger("mousewheel")
        },
        unmousewheel: function(e) {
            return this.unbind("mousewheel", e)
        }
    })
}),
function() {
    var e, t, n = function(e) {
        return e.$ = function(e) {
            return $(e, this.el)
        },
        e.el = $(e.el || "<div/>"),
        e.init && e.init(),
        _.each(e.events,
        function(t, n) {
            var i = n.indexOf(" ");
            e.el.on( - 1 == i ? n: n.slice(0, i), -1 == i ? "": $.trim(n.slice(i + 1)),
            function(n) {
                var i = _.isString(t) ? e[t] : t;
                return i ? i.call(this, n, e) : void 0
            })
        }),
        e.create && e.create(),
        e
    },
    i = window.ActiveXObject && !window.XMLHttpRequest,
    r = {
        sideWidth: 0,
        mask: !0,
        margin: [0, 0, 0, 0],
        padding: [10, 50, 80, 50],
        skin: "",
        thumb: !0,
        close: function(e, t) {
            t = t || this,
            t.el.remove(),
            $(window).off("resize.picasa"),
            $("body").removeClass("pica-body")
        },
        escExit: !0,
        layout: function() {
            var e = this,
            t = $(window),
            n = e.padding,
            r = t.width() - e.margin[3] - e.margin[1] - e.sideWidth - n[1] - n[3],
            o = t.height() - e.margin[0] - e.margin[2] - n[0] - n[2];
            e.sceneMax = {
                width: r,
                height: o,
                rate: r / o,
                x: r / 2 + n[3],
                y: o / 2 + n[0]
            },
            this.stage.css({
                top: e.margin[0],
                left: e.margin[3],
                right: e.margin[1] + e.sideWidth,
                bottom: e.margin[2]
            }),
            e.sideWidth && this.sidebar.css({
                width: e.sideWidth,
                top: e.margin[0],
                right: e.margin[1],
                bottom: e.margin[2]
            }),
            i && (this.stage.width(t.width() - e.margin[1] - e.margin[3] - e.sideWidth).height(t.height() - e.margin[0] - e.margin[2]), this.sidebar.height(t.height() - e.margin[0] - e.margin[2]));
            var s = e.data[e.active];
            s.src || e.scene.height(s.height = o).width(s.width = r)
        },
        load: function(e, t) {
            2 == e.readyState && t();
            var n = new Image,
            i = this;
            e.readyState > 0 || (e.readyState = 1, i.scene.empty().addClass("pica-loading"), n.onload = function() {
                e.readyState = 2,
                e._width = this.width,
                e._height = this.height,
                e.rate = this.width / this.height;
                var n, r, o = i.sceneMax;
                o.rate > e.rate ? (r = Math.min(e._height, o.height), n = r * e.rate) : (n = Math.min(e._width, o.width), r = n / e.rate),
                _.extend(e, {
                    width: n,
                    height: r,
                    scale: n / e._width,
                    left: o.x - n / 2,
                    top: o.y - r / 2
                }),
                i.scene.removeClass("pica-loading"),
                t()
            },
            n.onerror = function() {
                e.readyState = -1,
                i.scene.removeClass("pica-loading"),
                t()
            },
            n.src = e.src)
        },
        position: function() {
            var e = this.data[this.active];
            this.scene.css({
                width: e.width,
                height: e.height,
                left: e.left,
                top: e.top
            })
        },
        active: 0,
        onchange: $.noop,
        setTitle: function(e, t) {
            return '<span class="pica-page">' + e + "/" + this.data.length + "</span>" + (t.title || "")
        },
        prev: function(e, t) {
            t = t || this,
            t.play(t.active - 1)
        },
        next: function(e, t) {
            t = t || this,
            t.play(t.active + 1)
        },
        play: function(e) {
            var t = this,
            n = this.data.length - 1;
            if (! (0 > e || e > n)) {
                var i = this.data[e];
                this.prevBtn.toggle(0 !== e),
                this.nextBtn.toggle(e !== n),
                this.rotater.toggle( !! i.src),
                this.active = e,
                this.thumbUl.css("marginLeft", -15 - 32 * e),
                this.title.html(t.setTitle(e + 1, i) || ""),
                this.insert(e, i),
                this.onchange(i)
            }
        },
        rotate: function(e, t) {
            t = t || this;
            var n = t.data[t.active];
            n.src && (n.rotate = (n.rotate || 0) + 1, t.scene.children().get(0).style.cssText = t.rotateStyle(n))
        },
        rotateStyle: function(e, t) {
            return t && !e.rotate ? "": void 0 != this.cssPrefix ? this.cssPrefix + "transform:rotate(" + 90 * e.rotate + "deg)": "filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=" + (e.rotate + 4) % 4 + ")"
        },
        insert: function(t, n) {
            var i = this,
            r = i.sceneMax;
            if (n.src) i.scene.draggable("enable"),
            i.load(n,
            function() {
                if (t == i.active) {
                    2 == n.readyState && (i.cssPrefix && e && (clearTimeout(e), i.scene.addClass("pica-tranfix")), i.position(), i.cssPrefix && (e = setTimeout(function() {
                        i.scene.removeClass("pica-tranfix")
                    },
                    300)));
                    var r = i.rotateStyle(n, 1);
                    i.scene.html('<img src="' + n.src + '"' + (r ? ' style="' + r + '"': "") + ' class="pica-player pica-img">')
                }
            });
            else {
                i.scene.draggable("disable"),
                _.extend(n, {
                    width: r.width,
                    height: r.height,
                    left: i.padding[3],
                    top: i.padding[0]
                });
                var o = i.width ? ' style="width:' + i.width + 'px"': "";
                n.iframe ? i.scene.html("<iframe" + o + ' class="pica-player pica-frame scroll"  allowfullscreen="true" src="' + n.iframe + '"></iframe>') : i.scene.html("<div" + o + ' class="pica-player pica-html scroll">' + (n.html || "") + "</div>"),
                i.position()
            }
        },
        events: {
            "click .ac-pica-close": "close",
            "click .ac-pica-prev": "prev",
            "click .ac-pica-next": "next",
            "click .pica-rotate": "rotate",
            "click .pica-download": function(e, t) {
                var n = t.data[t.active];
                t.download(n)
            }
        },
        showTip: function(e) {
            var n = this;
            t ? clearTimeout(t) : n.stip.stop().show().fadeIn(),
            t = setTimeout(function() {
                n.stip.fadeOut(),
                t = 0
            },
            800),
            this.stip.html(e)
        },
        init: function() {
            var e = this;
            e.el.addClass("mask glass pica-body " + e.skin).html('<div class="pica-sidebar scroll am-fadeup"></div>                <div class="pica-stage  user-select am-fadeup">                    <div class="pica-scene"></div>                    <div class="pica-thumb"><ul></ul></div>                    <div class="pica-title"></div>                    <div class="pica-tool"><b class="pica-rotate pica-tile"><i class="pcon pcon-rotate"></i></b>' + (e.download ? '<b class="pica-download pica-tile"><i class="pcon pcon-down-gray"></i></b>': "") + '</div>                    <div class="pica-prev ac-pica-prev">&lt;</div>                    <div class="pica-next ac-pica-next">&gt;</div>                    <span class="pica-stip"></span>                </div>                <div class="pica-close ac-pica-close"></div>').appendTo($("body").addClass("pica-body")),
            e.escExit && e.el.attr("tabIndex", 1).keydown(function(t) {
                27 == t.keyCode && e.close()
            }).focus();
            var t = e.el[0].style;
            e.cssPrefix = _.find(["-webkit-", "-moz-", "-ms-", ""],
            function(e) {
                return e + "transform" in t
            }),
            e.stip = e.$(".pica-stip"),
            e.closeBtn = e.$(".pica-close"),
            e.prevBtn = e.$(".pica-prev"),
            e.nextBtn = e.$(".pica-next"),
            e.rotater = e.$(".pica-rotate"),
            e.stage = e.$(".pica-stage").on("mousewheel",
            function(t, n, i, r) {
                var o = e.data[e.active];
                if (o.src && "scale" in o) {
                    t.preventDefault(),
                    t.stopPropagation();
                    var s = n > 0 ? 1.15 : .85,
                    a = parseInt(o.scale * s * 100),
                    l = e.sceneMax;
                    if (1 > a || a > 1e4) return;
                    $(t.target).hasClass("pica-player") || (t = l.x < o.left || l.y < o.top || l.x > o.left + o.width || l.y > o.top + o.height ? {
                        offsetX: o.left + o.width / 2,
                        offsetY: o.top + o.height / 2
                    }: {
                        offsetX: l.x,
                        offsetY: l.y
                    }),
                    o.scale *= s,
                    o.width = o._width * o.scale,
                    o.height = o._height * o.scale,
                    o.left = t.offsetX - (t.offsetX - o.left) * s,
                    o.top = t.offsetY - (t.offsetY - o.top) * s,
                    e.showTip(a + "%"),
                    e.position()
                }
            }),
            e.scene = e.$(".pica-scene").draggable({
                drag: function(t, n) {
                    var i = e.data[e.active];
                    i.left = n.position.left,
                    i.top = n.position.top
                }
            });
            var n = e.thumb;
            e.thumb = e.$(".pica-thumb"),
            e.thumbUl = e.thumb.find("ul"),
            n ? (e.thumb.on("mousewheel",
            function(t, n, i, r) {
                t.preventDefault(),
                t.stopPropagation(),
                0 > n ? e.next() : e.prev()
            }), e.thumbUl.html(_.reduce(e.data,
            function(e, t, n) {
                return e + '<li><img index="' + n + '" title="' + (t.title || "") + '" class="am-fadeup" src="' + t.thumb + '"></li>'
            },
            "")).on("click", "img",
            function() {
                e.play( + this.getAttribute("index"))
            })) : e.thumb.hide(),
            e.title = e.$(".pica-title"),
            e.tool = e.$(".pica-tool"),
            e.sidebar = e.$(".pica-sidebar"),
            e.sideWidth && e.sidebar.show().width(e.sideWidth),
            e.layout(),
            e.play(e.active),
            $(window).on("resize.picasa",
            function() {
                e.layout()
            })
        }
    };
    $.picasa = function(e) {
        return n(_.create(r, e))
    }
} ();