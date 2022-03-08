use gateway;
select facturacion.id,facturacion.project_code,facturacion.project_name,facturacion.customer,facturacion.PO_status,facturacion.PO_NO,
facturacion.PO_Line_NO,facturacion.shipment_NO,facturacion.site_code,facturacion.site_name,facturacion.item_code,facturacion.item_desc,
facturacion.requested_qty,facturacion.due_qty,facturacion.billed_qty,facturacion.unit_price,facturacion.line_amount,facturacion.unit,
facturacion.payment_terms,facturacion.category,facturacion.bidding_area,facturacion.publish_date,facturacion.status_cierre,
asignaciones.id_usuario,usuarios.nombre,usuarios.ape_pat,usuarios.ape_mat,asignaciones.orden_compra_dt,asignaciones.importe,
asignaciones.total_pagar,asignaciones.status_facturacion from facturacion, asignaciones, usuarios where asignaciones.id_usuario = usuarios.id_usuario && facturacion.id = asignaciones.id;